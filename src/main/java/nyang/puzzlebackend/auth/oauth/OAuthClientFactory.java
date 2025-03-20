package nyang.puzzlebackend.auth.oauth;

import java.util.HashMap;
import java.util.Map;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthClient;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthClient;
import org.springframework.stereotype.Component;

@Component
public class OAuthClientFactory {

  private final Map<String, OAuthClient> oAuthClients;

  public OAuthClientFactory(
      KakaoOAuthClient kakaoOAuthProvider,
      GoogleOAuthClient googleOAuthProvider
  ) {
    this.oAuthClients = new HashMap<>();
    this.oAuthClients.put("kakao", kakaoOAuthProvider);
    this.oAuthClients.put("google", googleOAuthProvider);
  }

  public OAuthClient getOAuthClient(String providerName) {
    OAuthClient oAuthClient = oAuthClients.get(providerName);
    if (oAuthClient == null) {
      throw new IllegalArgumentException("지원하지 않는 OAuth 공급자입니다.");
    }
    return oAuthClient;
  }
}
