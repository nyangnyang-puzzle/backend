package nyang.puzzlebackend.api;

import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.api.common.ApiResponse;
import nyang.puzzlebackend.api.response.AccessRefreshTokenResponse;
import nyang.puzzlebackend.api.response.OAuthUriResponse;
import nyang.puzzlebackend.auth.AuthenticationService;
import nyang.puzzlebackend.auth.oauth.OAuthClientFactory;
import nyang.puzzlebackend.auth.oauth.OAuthCodeExchangeRequest;
import nyang.puzzlebackend.auth.oauth.OAuthId;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping(value = "/auth/oauth2/{oauthProvider}/endpoint")
  public ApiResponse<OAuthUriResponse> oauth2Endpoint(
      @PathVariable String oauthProvider,
      @RequestParam(value = "redirect-uri") String redirectUri
  ) {
    final var oAuthClient = oAuthClientFactory.getOAuthClient(oauthProvider);
    return ApiResponse.ok(new OAuthUriResponse(oAuthClient.getAuthorizationUri(redirectUri)));
  }

  @PostMapping("/auth/oauth2/{oauthProvider}/exchange")
  public ApiResponse<AccessRefreshTokenResponse> exchangeOAuthCodeForTokens(
      @RequestBody OAuthCodeExchangeRequest oauthCodeExchangeRequest,
      @PathVariable String oauthProvider
  ) {    
    final var oAuthClient = oAuthClientFactory.getOAuthClient(oauthProvider);
    final var oAuthMember = oAuthClient.findOAuthMember(oauthCodeExchangeRequest, oauthCodeExchangeRequest.redirectUri());
    final var tokenResponse = authenticationService.generateToken(OAuthId.from(oAuthMember), OAuthProvider.of(oauthProvider));
    return ApiResponse.ok(tokenResponse);
  }

}


