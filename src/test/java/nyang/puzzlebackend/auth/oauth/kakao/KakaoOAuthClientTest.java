package nyang.puzzlebackend.auth.oauth.kakao;

import static org.assertj.core.api.Assertions.assertThat;

import nyang.puzzlebackend.auth.TestOAuthClientConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class KakaoOAuthClientTest extends TestOAuthClientConfiguration {

  @Autowired
  KakaoOAuthClient kakaoOAuthProvider;

  @Autowired
  KakaoProperties kakaoProperties;

  @Test
  void getAuthorizationUrl_returnsCorrectUrl() {
    String uri = kakaoOAuthProvider.getAuthorizationUrl();
    assertThat(uri).isEqualTo(kakaoProperties.oauthEndpointUri()
        + "?response_type=" + kakaoProperties.responseType()
        + "&client_id=" + kakaoProperties.clientId()
        + "&redirect_uri=" + kakaoProperties.redirectUrl());
  }

  @Test
  void kakaoOAuthProvider_isNotNull() {
    assertThat(kakaoOAuthProvider).isNotNull();
  }
}