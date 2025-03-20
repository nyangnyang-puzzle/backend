package nyang.puzzlebackend.auth.oauth;

public record OAuthMember(
    String oauthId,
    String accessToken,
    String refreshToken
) { }
