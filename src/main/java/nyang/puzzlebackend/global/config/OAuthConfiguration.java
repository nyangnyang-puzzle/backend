package nyang.puzzlebackend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import nyang.puzzlebackend.auth.oauth.OAuthClientFactory;
import nyang.puzzlebackend.auth.oauth.google.GoogleOAuthClient;
import nyang.puzzlebackend.auth.oauth.google.GoogleProperties;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoOAuthClient;
import nyang.puzzlebackend.auth.oauth.kakao.KakaoProperties;
import nyang.puzzlebackend.global.logging.LoggingInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({
    KakaoProperties.class,
    GoogleProperties.class
})
@Import({OAuthClientFactory.class, KakaoOAuthClient.class, GoogleOAuthClient.class})
public class OAuthConfiguration {


  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  LoggingInterceptor loggingInterceptor() {
    return new LoggingInterceptor();
  }

}
