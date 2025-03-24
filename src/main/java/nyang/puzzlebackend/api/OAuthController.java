package nyang.puzzlebackend.api;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.auth.AuthenticationService;
import nyang.puzzlebackend.auth.jwt.AccessRefreshToken;
import nyang.puzzlebackend.auth.oauth.OAuthClientFactory;
import nyang.puzzlebackend.auth.oauth.OAuthFrontendProperties;
import nyang.puzzlebackend.auth.oauth.OAuthId;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RestController
public class OAuthController {

  private final OAuthClientFactory oAuthClientFactory;
  private final AuthenticationService authenticationService;
  private final OAuthFrontendProperties oAuthFrontendProperties;

  public OAuthController(OAuthClientFactory oAuthClientFactory,
      AuthenticationService authenticationService, OAuthFrontendProperties oAuthFrontendProperties) {
    this.oAuthClientFactory = oAuthClientFactory;
    this.authenticationService = authenticationService;
    this.oAuthFrontendProperties = oAuthFrontendProperties;
  }

  @GetMapping("/auth/oauth2/{provider}/sign-in")
  public ResponseEntity<?> oauth2Endpoint(@PathVariable String provider) {
    final var oAuthClient = oAuthClientFactory.getOAuthClient(provider);
    return ResponseEntity.status(302)
        .location(URI.create(oAuthClient.getAuthorizationUrl()))
        .build();
  }

  @GetMapping("/auth/oauth2/{provider}/code")
  public ResponseEntity<?> callback(
      OAuthTokenRequest tokenRequest,
      @PathVariable String provider
  ) {
    var oAuthClient = oAuthClientFactory.getOAuthClient(provider);
    var oAuthMember = oAuthClient.findOAuthMember(tokenRequest);
    var token = authenticationService.generateToken(OAuthId.from(oAuthMember), OAuthProvider.of(provider));
    return createOAuthRedirectResponse(token);
  }

  private ResponseEntity<Void> createOAuthRedirectResponse(AccessRefreshToken token) {
    var headers = new HttpHeaders();
    headers.add(HttpHeaders.SET_COOKIE, createCookie("accessToken", token.accessToken()));
    headers.add(HttpHeaders.SET_COOKIE, createCookie("refreshToken", token.refreshToken()));
    return ResponseEntity.status(302)
        .location(buildRedirectUri())
        .build();
  }

  private URI buildRedirectUri() {
    return UriComponentsBuilder.fromUriString(oAuthFrontendProperties.frontDomain())
        .port(oAuthFrontendProperties.port())
        .path(oAuthFrontendProperties.successPath())
        .build()
        .toUri();
  }

  private String createCookie(String name, String value) {
    return String.format("%s=%s; Path=/; HttpOnly", name, value);
  }
}


