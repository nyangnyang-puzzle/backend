package nyang.puzzlebackend.auth.oauth.kakao;

import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import nyang.puzzlebackend.auth.oauth.OAuthClient;
import nyang.puzzlebackend.auth.oauth.OAuthToken;
import nyang.puzzlebackend.auth.oauth.OAuthMember;
import org.springframework.stereotype.Component;

@Component
public class KakaoOAuthClient implements OAuthClient {

  private final KakaoProperties kakaoProperties;

  public KakaoOAuthClient(KakaoProperties kakaoProperties) {
    this.kakaoProperties = kakaoProperties;
  }

  @Override
  public String getAuthorizationUrl() {
    return kakaoProperties.oauthEndpointUri()
        + "?response_type=" + kakaoProperties.responseType()
        + "&client_id=" + kakaoProperties.clientId()
        + "&redirect_uri=" + kakaoProperties.redirectUrl();
  }

  @Override
  public OAuthToken getAccessToken(OAuthTokenRequest authRequest) {
    return null;
  }

  @Override
  public OAuthMember getUserProfile(OAuthToken oAuthToken) {
    return null;
  }
}
