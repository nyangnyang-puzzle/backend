package nyang.puzzlebackend.auth.oauth;

public interface OAuthClient {
  String getAuthorizationUrl();
  OAuthToken getAccessToken(OAuthTokenRequest authRequest);
  OAuthMember getUserProfile(OAuthToken oAuthToken);
}
