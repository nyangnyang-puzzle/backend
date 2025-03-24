package nyang.puzzlebackend.api.response;

public record AccessRefreshTokenResponse(
    String accessToken,
    String refreshToken,
    String nickname
) { }
