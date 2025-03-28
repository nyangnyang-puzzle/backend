package nyang.puzzlebackend.auth.oauth.google;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.auth.oauth.OAuthClient;
import nyang.puzzlebackend.auth.oauth.OAuthMember;
import nyang.puzzlebackend.auth.oauth.OAuthToken;
import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import nyang.puzzlebackend.global.logging.LoggingInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class GoogleOAuthClient implements OAuthClient {

  private final GoogleProperties googleProperties;
  private final RestClient restClient;
  private final LoggingInterceptor loggingInterceptor;
  private final ObjectMapper objectMapper;

  public GoogleOAuthClient(GoogleProperties googleProperties, LoggingInterceptor loggingInterceptor,
      ObjectMapper objectMapper) {
    this.googleProperties = googleProperties;
    this.loggingInterceptor = loggingInterceptor;
    this.restClient = RestClient.builder()
        .requestInterceptor(loggingInterceptor)
        .build();
    this.objectMapper = objectMapper;
  }

  @Override
  public String getAuthorizationUrl() {
    return googleProperties.oauthEndpointUri()
        + "?response_type=code"
        + "&access_type=offline"
        + "&client_id=" + googleProperties.clientId()
        + "&redirect_uri=" + googleProperties.redirectUrl()
        + "&scope=" + googleProperties.scope()[0];
  }

  @Override
  public OAuthMember findOAuthMember(OAuthTokenRequest authRequest) {
    OAuthToken oAuthToken = getOAuthToken(authRequest.code());
    DecodedJWT decoder = JWT.decode(oAuthToken.idToken());
    String oauthId = decoder.getClaim("sub").asString();
    return new OAuthMember(oauthId, oAuthToken.accessToken(), oAuthToken.refreshToken());
  }

  private OAuthToken getOAuthToken(String code) {
    final var formData = setBody(code);
    return restClient.post()
        .uri(googleProperties.oauthTokenIssueUri())
        .header("Content-Type", "application/x-www-form-urlencoded")
        .body(formData)
        .retrieve()
        .body(OAuthToken.class);
  }

  private MultiValueMap<String, String> setBody(String code) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("code", code);
    formData.add("grant_type", "authorization_code");
    formData.add("client_id", googleProperties.clientId());
    formData.add("redirect_uri", googleProperties.redirectUrl());
    formData.add("client_secret", googleProperties.clientSecret());
    return formData;
  }
}
