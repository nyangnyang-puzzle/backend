package nyang.puzzlebackend.auth;

import static org.assertj.core.api.Assertions.assertThat;

import nyang.puzzlebackend.auth.oauth.OAuthClientFactory;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthClient;
import nyang.puzzlebackend.auth.oauth.google.GoogleProperties;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthClient;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoProperties;
import nyang.puzzlebackend.global.config.OAuthConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    initializers = ConfigDataApplicationContextInitializer.class,
    classes = {OAuthConfiguration.class})
@TestPropertySource(locations = "classpath:application-test.yml")
public class TestOAuthClientConfiguration {

    @Autowired
    private ApplicationContext context;

    @Test
    void oauthBeansLoadWithOAuthConfiguration() {
      assertThat(context.getBean(OAuthConfiguration.class)).isNotNull();
      assertThat(context.getBean(KakaoProperties.class)).isNotNull();
      assertThat(context.getBean(GoogleProperties.class)).isNotNull();
      assertThat(context.getBean(OAuthClientFactory.class)).isNotNull();
      assertThat(context.getBean(KakaoOAuthClient.class)).isNotNull();
      assertThat(context.getBean(GoogleOAuthClient.class)).isNotNull();
    }
}