package nyang.puzzlebackend.api;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.api.common.ApiResponse;
import nyang.puzzlebackend.api.response.OAuthUriResponse;
import nyang.puzzlebackend.auth.AuthenticationService;
import nyang.puzzlebackend.auth.oauth.OAuthClientFactory;
import nyang.puzzlebackend.auth.oauth.OAuthId;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OAuthController {

  private final OAuthClientFactory oAuthClientFactory;
  private final AuthenticationService authenticationService;

  public OAuthController(OAuthClientFactory oAuthClientFactory, AuthenticationService authenticationService) {
    this.oAuthClientFactory = oAuthClientFactory;
    this.authenticationService = authenticationService;
  }

  @GetMapping(value = "/auth/oauth2/{provider}/endpoint")
  public ApiResponse<OAuthUriResponse> oauth2Endpoint(@PathVariable String provider) {
    final var oAuthClient = oAuthClientFactory.getOAuthClient(provider);
    return ApiResponse.ok(new OAuthUriResponse(oAuthClient.getAuthorizationUrl()));
  }

  @GetMapping("/auth/oauth2/{provider}/code")
  public ResponseEntity<?> callback(
      OAuthTokenRequest tokenRequest,
      @PathVariable String provider
  ) {
    var oAuthClient = oAuthClientFactory.getOAuthClient(provider);
    var oAuthMember = oAuthClient.findOAuthMember(tokenRequest);
    var token = authenticationService.generateToken(OAuthId.from(oAuthMember), OAuthProvider.of(provider));
    return ResponseEntity.status(302)
        .header(HttpHeaders.SET_COOKIE, "accessToken=%s; Path=/; HttpOnly".formatted(token.accessToken()))
        .header(HttpHeaders.SET_COOKIE, "refreshToken=%s; Path=/; HttpOnly".formatted(token.refreshToken()))
        .location(URI.create("http://localhost:3000/main"))
        .build();
  }

}


