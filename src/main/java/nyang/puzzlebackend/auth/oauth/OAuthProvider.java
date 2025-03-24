package nyang.puzzlebackend.auth.oauth;

import java.util.Arrays;

public enum OAuthProvider {
  GOOGLE,
  KAKAO;

  public static OAuthProvider of(String provider) {
    return Arrays.stream(OAuthProvider.values())
        .filter(p -> p.name().equalsIgnoreCase(provider))
        .findFirst()
        .orElseThrow();
  }
}
