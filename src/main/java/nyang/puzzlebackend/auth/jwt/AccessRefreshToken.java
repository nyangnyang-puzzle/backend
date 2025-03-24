package nyang.puzzlebackend.auth.jwt;

import nyang.puzzlebackend.api.response.AccessRefreshTokenResponse;

public record AccessRefreshToken(
    String accessToken,
    String refreshToken
) {

  public static AccessRefreshTokenResponse newUser(String accessToken, String refreshToken) {
    return new AccessRefreshTokenResponse(accessToken, refreshToken, null);
  }

  public static AccessRefreshTokenResponse of(String accessToken, String refreshToken, String nickname) {
    return new AccessRefreshTokenResponse(accessToken, refreshToken, nickname);
  }
}
