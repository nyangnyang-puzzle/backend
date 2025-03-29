package nyang.puzzlebackend.auth.oauth;

public record OAuthCodeExchangeRequest(
    String code,
    String redirectUri
) { }