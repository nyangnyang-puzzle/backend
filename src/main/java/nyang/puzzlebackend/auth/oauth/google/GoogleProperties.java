package nyang.puzzlebackend.auth.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.google")
public record GoogleProperties(
    String clientId,
    String clientSecret,
    String oauthEndpointUri,
    String redirectUrl,
    String oauthTokenIssueUri,
    String oauthUserInfoUri,
    String oauthUnlinkUri,
    String responseType,
    String[] scope
) {}