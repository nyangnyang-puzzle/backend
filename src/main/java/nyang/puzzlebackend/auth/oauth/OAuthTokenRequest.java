package nyang.puzzlebackend.auth.oauth;

public record OAuthTokenRequest(
    String code,
    String error,
    String error_description,
    String state
) { }