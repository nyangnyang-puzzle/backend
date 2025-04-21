package nyang.puzzlebackend.auth;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.api.response.AccessRefreshTokenResponse;
import nyang.puzzlebackend.auth.jwt.AccessRefreshToken;
import nyang.puzzlebackend.auth.jwt.JwtTokenProvider;
import nyang.puzzlebackend.auth.oauth.OAuthId;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import nyang.puzzlebackend.domain.user.User;
import nyang.puzzlebackend.domain.user.UserRepository;
import nyang.puzzlebackend.global.error.AuthenticationException;
import nyang.puzzlebackend.global.error.ErrorCode;
import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
    String accessToken = jwtTokenProvider.issueAccessToken(user.getId().toString());
    String refreshToken = jwtTokenProvider.issueRefreshToken(user.getId().toString());
    saveRefreshToken(user.getId(), refreshToken);
    if (user.getNickname() == null) {
      return AccessRefreshToken.newUser(accessToken, refreshToken);
    }
    return AccessRefreshToken.of(accessToken, refreshToken, user.getNickname());
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

  public AppUser findUserByToken(String token) {
    if (!jwtTokenProvider.isValidToken(token)) {
      throw new AuthenticationException(ErrorCode.A002);
    }
    return new AppUser(jwtTokenProvider.parseUserId(token));
  }

  public void checkAuthentication(HttpServletRequest request) {
    final String token = jwtTokenProvider.parseTokenFromHeader(request);
    if (!jwtTokenProvider.isValidToken(token)) {
      log.warn("Invalid JWT token received from IP: {}, URI: {}",
          request.getRemoteAddr(), request.getRequestURI());
      throw new AuthenticationException(ErrorCode.A002);
    }
  }
}
