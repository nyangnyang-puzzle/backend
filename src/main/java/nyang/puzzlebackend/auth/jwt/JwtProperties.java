package nyang.puzzlebackend.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
    String secret,
    long accessTimeSec,
    long refreshTimeSec
) { }
