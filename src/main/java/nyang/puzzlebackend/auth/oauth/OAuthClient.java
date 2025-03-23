package nyang.puzzlebackend.auth.oauth;

public interface OAuthClient {
  String getAuthorizationUrl();
  OAuthMember findOAuthMember(OAuthTokenRequest authRequest);
}
