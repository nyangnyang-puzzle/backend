package nyang.puzzlebackend.global.config;

import nyang.puzzlebackend.auth.oauth.OAuthFrontendProperties;
import nyang.puzzlebackend.auth.oauth.OAuthClientFactory;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthClient;
import nyang.puzzlebackend.auth.oauth.google.GoogleProperties;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthClient;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({
    KakaoProperties.class,
    GoogleProperties.class,
    OAuthFrontendProperties.class
})
@Import({OAuthClientFactory.class, KakaoOAuthClient.class, GoogleOAuthClient.class})
public class OAuthConfiguration {
}
