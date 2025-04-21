package nyang.puzzlebackend.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nyang.puzzlebackend.auth.jwt.JwtTokenProvider;
import nyang.puzzlebackend.global.error.AuthenticationException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class OptionalAuthPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

  private final JwtTokenProvider tokenProvider;
  private final AuthenticationService authenticationService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType() == AppUser.class
        && parameter.hasParameterAnnotation(OptionalAuthPrincipal.class);
  }

  @Override
  public Optional<AppUser> resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory) {
    try {
      var request = webRequest.getNativeRequest(HttpServletRequest.class);
      var token = tokenProvider.parseTokenFromHeader(request);
      return Optional.of(authenticationService.findUserByToken(token));
    } catch (AuthenticationException e) {
      return Optional.empty();
    }
  }

}
