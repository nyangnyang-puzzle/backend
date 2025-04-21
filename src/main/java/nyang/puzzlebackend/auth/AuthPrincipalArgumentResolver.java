package nyang.puzzlebackend.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nyang.puzzlebackend.auth.jwt.JwtTokenProvider;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

  private final JwtTokenProvider tokenProvider;
  private final AuthenticationService authenticationService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType() == AppUser.class
        && parameter.hasParameterAnnotation(AuthPrincipal.class);
  }

  @Override
  public AppUser resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory
  ) {
    var request = webRequest.getNativeRequest(HttpServletRequest.class);
    var token = tokenProvider.parseTokenFromHeader(request);
    return authenticationService.findUserByToken(token);
  }

}
