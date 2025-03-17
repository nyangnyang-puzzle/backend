package nyang.puzzlebackend.auth.oauth;

public record OAuthAuthorization(
    String code,
    String error,
    String error_description,
    String state
) { }