package nyang.puzzlebackend.auth.oauth.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.auth.oauth.OAuthToken;
import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import nyang.puzzlebackend.auth.oauth.OAuthClient;
import nyang.puzzlebackend.auth.oauth.OAuthMember;
import nyang.puzzlebackend.global.logging.LoggingInterceptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class KakaoOAuthClient implements OAuthClient {

  private final RestClient kakaoRestClient;
  private final KakaoProperties kakaoProperties;
  private final ObjectMapper objectMapper;
  private final LoggingInterceptor loggingInterceptor;

  public KakaoOAuthClient(KakaoProperties kakaoProperties, ObjectMapper objectMapper,
      LoggingInterceptor loggingInterceptor) {
    this.kakaoProperties = kakaoProperties;
    this.loggingInterceptor = loggingInterceptor;
    this.kakaoRestClient = RestClient.builder()
        .requestInterceptor(loggingInterceptor)
        .build();
    this.objectMapper = objectMapper;
  }

  @Override
  public String getAuthorizationUrl() {
    return kakaoProperties.oauthEndpointUri()
        + "?response_type=" + kakaoProperties.responseType()
        + "&client_id=" + kakaoProperties.clientId()
        + "&redirect_uri=" + kakaoProperties.redirectUrl();
  }

  @Override
  public OAuthMember findOAuthMember(OAuthTokenRequest authRequest) {
    OAuthToken oAuthToken = getOAuthToken(authRequest.code());
    return findKakaoMember(oAuthToken);
  }

  private OAuthMember findKakaoMember(OAuthToken oAuthToken) {
    var header = kakaoUserRequestHeader(oAuthToken.accessToken());
    var responseBody = findKakaoUserInfo(header);
    try {
      JsonNode jsonNode = objectMapper.readTree(responseBody);
      long id = jsonNode.get("id").asLong();
      return OAuthMember.of(
          String.valueOf(id),
          oAuthToken.accessToken(),
          oAuthToken.refreshToken()
      );
    } catch (Exception e) {
      throw new RuntimeException("error");
    }
  }

  private String findKakaoUserInfo(HttpHeaders headers) {
    return kakaoRestClient.get()
        .uri(kakaoProperties.oauthUserInfoUri())
        .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
        .headers(httpHeaders -> httpHeaders.addAll(headers))
        .retrieve()
        .body(String.class);
  }

  private HttpHeaders kakaoUserRequestHeader(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);
    return headers;
  }

  private OAuthToken getOAuthToken(String code) {
    final var formData = setBody(code);
    var responseEntity = kakaoRestClient.post()
        .uri(kakaoProperties.oauthTokenIssueUri())
        .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
        .body(formData)
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
            (request, response) -> {
              log.error("Request: {} {}, Response: {} {}", request.getURI(), request.getMethod(),
                  response.getStatusCode(), response.getStatusText());
            })
        .toEntity(OAuthToken.class);
    log.info("responseEntity.getStatusCode() : {}", responseEntity.getStatusCode());
    return responseEntity.getBody();
  }

  private MultiValueMap<String, String> setBody(String code) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("code", code);
    formData.add("grant_type", "authorization_code");
    formData.add("client_id", kakaoProperties.clientId());
    formData.add("redirect_uri", kakaoProperties.redirectUrl());
    formData.add("client_secret", kakaoProperties.clientSecret());
    return formData;
  }
}
