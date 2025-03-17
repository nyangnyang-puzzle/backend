package nyang.puzzlebackend.auth.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoProperties(
    String clientId,
    String clientSecret,
    String oauthEndpointUri,
    String redirectUrl,
    String oauthTokenIssueUri,
    String oauthUserInfoUri,
    String oauthUnlinkUri,
    String responseType
) {}