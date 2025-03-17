package nyang.puzzlebackend.auth.oauth.google;

import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import org.springframework.stereotype.Component;

@Component
public class GoogleOAuthProvider implements OAuthProvider {

  private final GoogleProperties googleProperties;

  public GoogleOAuthProvider(GoogleProperties googleProperties) {
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
}
