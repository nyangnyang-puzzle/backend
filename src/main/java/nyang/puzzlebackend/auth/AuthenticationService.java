package nyang.puzzlebackend.auth;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import nyang.puzzlebackend.api.response.AccessRefreshTokenResponse;
import nyang.puzzlebackend.auth.jwt.AccessRefreshToken;
import nyang.puzzlebackend.auth.jwt.JwtTokenProvider;
import nyang.puzzlebackend.auth.oauth.OAuthId;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import nyang.puzzlebackend.domain.user.User;
import nyang.puzzlebackend.domain.user.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisTemplate<String, String> redisTemplate;

  public AuthenticationService(
      UserRepository userRepository,
      JwtTokenProvider jwtTokenProvider,
      RedisTemplate<String, String> redisTemplate
  ) {
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
    this.redisTemplate = redisTemplate;
  }

  public AccessRefreshTokenResponse generateToken(OAuthId oAuthId, OAuthProvider oAuthProvider) {
    User user = findOAuthUser(oAuthId, oAuthProvider);
    String accessToken = jwtTokenProvider.issueAccessToken(user.id().toString());
    String refreshToken = jwtTokenProvider.issueRefreshToken(user.id().toString());
    saveRefreshToken(user.id(), refreshToken);
    if (user.nickname() == null) {
      return AccessRefreshToken.newUser(accessToken, refreshToken);
    }
    return AccessRefreshToken.of(accessToken, refreshToken, user.nickname());
  }

  private void saveRefreshToken(ObjectId userId, String refreshToken) {
    redisTemplate.opsForValue().set(
        refreshToken,
        userId.toString(),
        Duration.of(jwtTokenProvider.getJwtProperties().refreshTimeSec(), ChronoUnit.SECONDS)
    );
  }

  private User findOAuthUser(OAuthId oAuthId, OAuthProvider provider) {
    return userRepository.findByOauthId(oAuthId.id())
        .orElseGet(() -> userRepository.save(oAuthId.toUserWithProvider(provider)));
  }
}
