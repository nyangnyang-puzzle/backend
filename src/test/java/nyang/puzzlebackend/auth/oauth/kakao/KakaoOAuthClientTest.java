package nyang.puzzlebackend.auth.oauth.kakao;

import static org.assertj.core.api.Assertions.assertThat;

import nyang.puzzlebackend.auth.TestOAuthClientConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class KakaoOAuthClientTest extends TestOAuthClientConfiguration {

  @Autowired
  KakaoOAuthClient kakaoOAuthProvider;

  @Test
  void kakaoOAuthProvider_isNotNull() {
    assertThat(kakaoOAuthProvider).isNotNull();
  }
}