package nyang.puzzlebackend.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nyang.puzzlebackend.auth.AuthPrincipalArgumentResolver;
import nyang.puzzlebackend.auth.AuthenticationInterceptor;
import nyang.puzzlebackend.auth.OptionalAuthPrincipalArgumentResolver;
import nyang.puzzlebackend.global.logging.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final AuthenticationInterceptor authenticationInterceptor;
  private final OptionalAuthPrincipalArgumentResolver optionalAuthPrincipalArgumentResolver;
  private final AuthPrincipalArgumentResolver authPrincipalArgumentResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticationInterceptor)
        .order(1)
        .excludePathPatterns(
            // nickname
            "/user/nickname/available-check/**",

            // auth
            "/auth/oauth2/**"
        );

  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(authPrincipalArgumentResolver);
    resolvers.add(optionalAuthPrincipalArgumentResolver);
  }

  @Bean
  LoggingInterceptor loggingInterceptor() {
    return new LoggingInterceptor();
  }
}
