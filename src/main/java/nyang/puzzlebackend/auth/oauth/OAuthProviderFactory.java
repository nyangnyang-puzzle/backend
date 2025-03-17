package nyang.puzzlebackend.auth.oauth;

import java.util.HashMap;
import java.util.Map;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthProvider;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthProvider;
import org.springframework.stereotype.Component;

@Component
public class OAuthProviderFactory {

  private final Map<String, OAuthProvider> providers;

  public OAuthProviderFactory(
      KakaoOAuthProvider kakaoOAuthProvider,
      GoogleOAuthProvider googleOAuthProvider
  ) {
    this.providers = new HashMap<>();
    this.providers.put("kakao", kakaoOAuthProvider);
    this.providers.put("google", googleOAuthProvider);
  }

  public OAuthProvider getOAuthProvider(String providerName) {
    OAuthProvider oAuthProvider = providers.get(providerName);
    if (oAuthProvider == null) {
      throw new IllegalArgumentException("지원하지 않는 OAuth 공급자입니다.");
    }
    return oAuthProvider;
  }
}
