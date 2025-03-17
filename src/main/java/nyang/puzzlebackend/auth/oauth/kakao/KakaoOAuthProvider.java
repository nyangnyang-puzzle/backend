package nyang.puzzlebackend.auth.oauth.kakao;

import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthProvider implements OAuthProvider {

  private final KakaoProperties kakaoProperties;

  public KakaoOAuthProvider(KakaoProperties kakaoProperties) {
    this.kakaoProperties = kakaoProperties;
  }

  @Override
  public String getAuthorizationUrl() {
    return kakaoProperties.oauthEndpointUri()
        + "?response_type=" + kakaoProperties.responseType()
        + "&client_id=" + kakaoProperties.clientId()
        + "&redirect_uri=" + kakaoProperties.redirectUrl();
  }
}
