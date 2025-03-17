package nyang.puzzlebackend.global.config;

import nyang.puzzlebackend.auth.oauth.OAuthFrontendProperties;
import nyang.puzzlebackend.auth.oauth.OAuthProviderFactory;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthProvider;
import nyang.puzzlebackend.auth.oauth.google.GoogleProperties;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthProvider;
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
@Import({OAuthProviderFactory.class, KakaoOAuthProvider.class, GoogleOAuthProvider.class})
public class OAuthConfiguration {
}
