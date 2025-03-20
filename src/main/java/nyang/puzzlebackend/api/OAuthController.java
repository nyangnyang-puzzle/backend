package nyang.puzzlebackend.api;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import nyang.puzzlebackend.auth.oauth.OAuthClientFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OAuthController {

  private final OAuthClientFactory oAuthClientFactory;

  public OAuthController(OAuthClientFactory oAuthClientFactory) {
    this.oAuthClientFactory = oAuthClientFactory;
  }

  @GetMapping("/auth/oauth2/{provider}/sign-in")
  public ResponseEntity<?> oauth2Endpoint(@PathVariable String provider) {
    final var oAuthClient = oAuthClientFactory.getOAuthClient(provider);
    return ResponseEntity.status(302)
        .location(URI.create(oAuthClient.getAuthorizationUrl()))
        .build();
  }
}


