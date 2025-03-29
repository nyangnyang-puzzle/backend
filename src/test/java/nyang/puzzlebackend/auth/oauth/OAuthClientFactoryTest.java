package nyang.puzzlebackend.auth.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import nyang.puzzlebackend.auth.TestOAuthClientConfiguration;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthClient;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

class OAuthClientFactoryTest extends TestOAuthClientConfiguration {

  @Autowired
  private OAuthClientFactory factory;

  @ParameterizedTest
  @MethodSource("providerArguments")
  void getOAuthProvider_returnsCorrectClient(String resource, Class<?> expectedProviderClass) {
    OAuthClient provider = factory.getOAuthClient(resource);
    assertThat(provider).isInstanceOf(expectedProviderClass);
  }

  static Stream<Arguments> providerArguments() {
    return Stream.of(
        Arguments.of("kakao", KakaoOAuthClient.class),
        Arguments.of("google", GoogleOAuthClient.class)
    );
  }

  @Test
  void getOAuthProvider_unknownClient_throwsException() {
    assertThatThrownBy(() -> factory.getOAuthClient("unknown"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}