package nyang.puzzlebackend.auth;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import nyang.puzzlebackend.auth.jwt.AccessRefreshToken;
import nyang.puzzlebackend.auth.jwt.JwtTokenProvider;
import nyang.puzzlebackend.auth.oauth.OAuthId;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import nyang.puzzlebackend.domain.user.User;
import nyang.puzzlebackend.repository.UserRepository;
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

  public AccessRefreshToken generateToken(OAuthId oAuthId, OAuthProvider oAuthProvider) {
    User user = findOAuthUser(oAuthId, oAuthProvider);
    String accessToken = jwtTokenProvider.issueAccessToken(user.id().toString());
    String refreshToken = jwtTokenProvider.issueRefreshToken(user.id().toString());
    saveRefreshToken(user.id(), refreshToken);
    return new AccessRefreshToken(accessToken, refreshToken);
  }

  private void saveRefreshToken(ObjectId userId, String refreshToken) {
    redisTemplate.opsForValue().set(refreshToken, userId.toString(), Duration.of(86400, ChronoUnit.SECONDS));
  }

  private User findOAuthUser(OAuthId oAuthId, OAuthProvider provider) {
    return userRepository.findByOauthId(oAuthId.id())
        .orElseGet(() -> userRepository.save(oAuthId.toUserWithProvider(provider)));
  }
}
