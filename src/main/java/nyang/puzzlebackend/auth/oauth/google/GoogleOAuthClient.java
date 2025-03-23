package nyang.puzzlebackend.auth.oauth.google;

import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import nyang.puzzlebackend.auth.oauth.OAuthClient;
import nyang.puzzlebackend.auth.oauth.OAuthToken;
import nyang.puzzlebackend.auth.oauth.OAuthMember;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthClient implements OAuthClient {

  private final GoogleProperties googleProperties;

  public GoogleOAuthClient(GoogleProperties googleProperties) {
    this.googleProperties = googleProperties;
  }

  @Override
  public String getAuthorizationUrl() {
    return googleProperties.oauthEndpointUri()
        + "?response_type=code"
        + "&client_id=" + googleProperties.clientId()
        + "&redirect_uri=" + googleProperties.redirectUrl()
        + "&scope=" + googleProperties.scope()[0];
  }

  @Override
  public OAuthMember findOAuthMember(OAuthTokenRequest authRequest) {
    return null;
  }
}
