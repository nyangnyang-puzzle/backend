package nyang.puzzlebackend.auth.oauth;

public interface OAuthClient {
  String getAuthorizationUri(String redirectUri);
  OAuthMember findOAuthMember(OAuthCodeExchangeRequest authRequest, String redirectUri);
}
