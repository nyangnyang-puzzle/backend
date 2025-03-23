package nyang.puzzlebackend.auth.jwt;

public record AccessRefreshToken(
    String accessToken,
    String refreshToken
) { }
