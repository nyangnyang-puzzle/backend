package nyang.puzzlebackend.auth.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OAuthToken(

    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("refresh_token")
    String refreshToken,

    @JsonProperty("expires_in")
    Integer accessExpiresIn,

    @JsonProperty("refresh_token_expires_in")
    Integer refreshExpiresIn,

    @JsonProperty("id_token")
    String idToken
) {

}