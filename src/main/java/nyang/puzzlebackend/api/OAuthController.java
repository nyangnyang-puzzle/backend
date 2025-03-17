package nyang.puzzlebackend.api;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.auth.oauth.OAuthProviderFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OAuthController {

  private final OAuthProviderFactory oAuthProviderFactory;

  public OAuthController(OAuthProviderFactory oAuthProviderFactory) {
    this.oAuthProviderFactory = oAuthProviderFactory;
  }

  @GetMapping("/auth/oauth2/{provider}/sign-in")
  public ResponseEntity<?> oauth2Endpoint(@PathVariable String provider) {
    final var oAuthProvider = oAuthProviderFactory.getOAuthProvider(provider);
    return ResponseEntity.status(302)
        .location(URI.create(oAuthProvider.getAuthorizationUrl()))
        .build();
  }
}


