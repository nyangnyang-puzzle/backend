package nyang.puzzlebackend.auth.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import nyang.puzzlebackend.auth.TestOAuthProviderConfiguration;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthProvider;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

class OAuthProviderFactoryTest extends TestOAuthProviderConfiguration {

  @Autowired
  private OAuthProviderFactory factory;

  @ParameterizedTest
  @MethodSource("providerArguments")
  void getOAuthProvider_returnsCorrectProvider(String resource, Class<?> expectedProviderClass) {
    OAuthProvider provider = factory.getOAuthProvider(resource);
    assertThat(provider).isInstanceOf(expectedProviderClass);
  }

  static Stream<Arguments> providerArguments() {
    return Stream.of(
        Arguments.of("kakao", KakaoOAuthProvider.class),
        Arguments.of("google", GoogleOAuthProvider.class)
    );
  }

  @Test
  void getOAuthProvider_unknownProvider_throwsException() {
    assertThatThrownBy(() -> factory.getOAuthProvider("unknown"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}