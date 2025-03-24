package nyang.puzzlebackend.auth.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.callback")
public record OAuthFrontendProperties(
    String frontDomain,
    String successPath,
    String errorPath,
    int port
) {}
