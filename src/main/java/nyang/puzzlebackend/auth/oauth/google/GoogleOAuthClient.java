package nyang.puzzlebackend.auth.oauth.google;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.auth.oauth.OAuthClient;
import nyang.puzzlebackend.auth.oauth.OAuthMember;
import nyang.puzzlebackend.auth.oauth.OAuthToken;
import nyang.puzzlebackend.auth.oauth.OAuthCodeExchangeRequest;
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

  public GoogleOAuthClient(GoogleProperties googleProperties, LoggingInterceptor loggingInterceptor) {
    this.googleProperties = googleProperties;
    this.restClient = RestClient.builder()
        .requestInterceptor(loggingInterceptor)
        .build();
  }

  @Override
  public String getAuthorizationUri(String redirectUri) {
    return googleProperties.oauthEndpointUri()
        + "?response_type=code"
        + "&access_type=offline"
        + "&client_id=" + googleProperties.clientId()
        + "&redirect_uri=" + redirectUri
        + "&scope=" + googleProperties.scope()[0];
  }

  @Override
  public OAuthMember findOAuthMember(OAuthCodeExchangeRequest authRequest, String redirectUri) {
    OAuthToken oAuthToken = getOAuthToken(authRequest.code(), redirectUri);
    DecodedJWT decoder = JWT.decode(oAuthToken.idToken());
    String oauthId = decoder.getClaim("sub").asString();
    return new OAuthMember(oauthId, oAuthToken.accessToken(), oAuthToken.refreshToken());
  }

  private OAuthToken getOAuthToken(String code, String redirectUri) {
    final var formData = setBody(code, redirectUri);
    return restClient.post()
        .uri(googleProperties.oauthTokenIssueUri())
        .body(formData)
        .retrieve()
        .body(OAuthToken.class);
  }

  private MultiValueMap<String, String> setBody(String code, String redirectUri) {
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("code", code);
    formData.add("grant_type", "authorization_code");
    formData.add("client_id", googleProperties.clientId());
    formData.add("redirect_uri", redirectUri);
    formData.add("client_secret", googleProperties.clientSecret());
    return formData;
  }
}
