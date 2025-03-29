package nyang.puzzlebackend.auth.oauth;

import nyang.puzzlebackend.domain.user.User;

public record OAuthId(String id) {

  public static OAuthId from(OAuthMember oAuthMember) {
    return new OAuthId(oAuthMember.oauthId());
  }

  public User toUserWithProvider(OAuthProvider oAuthProvider) {
    return new User(id, oAuthProvider);
  }

}
