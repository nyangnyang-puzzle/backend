package nyang.puzzlebackend.auth.oauth;

public record OAuthMember(
    String oauthId,
    String accessToken,
    String refreshToken
) {

  public static OAuthMember of(String oauthId, String accessToken, String refreshToken) {
    return new OAuthMember(oauthId, accessToken, refreshToken);
  }
}
