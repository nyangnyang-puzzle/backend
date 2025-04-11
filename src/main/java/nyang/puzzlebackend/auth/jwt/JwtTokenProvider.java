package nyang.puzzlebackend.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtTokenProvider {

  private static final String UID = "uid";
  private static final String TOKEN_TYPE = "token_type";
  private static final String EXP = "exp";
  private static final String IAT = "iat";

  private final Algorithm AL;
  private final JwtProperties jwtProperties;

  public JwtTokenProvider(JwtProperties jwtProperties) {
    this.AL = Algorithm.HMAC512(jwtProperties.secret());
    this.jwtProperties = jwtProperties;
  }

  public enum TokenType {
    ACCESS,
    REFRESH;

    public final static List<TokenType> tokens = Arrays.stream(TokenType.values()).toList();

    public static TokenType of(String type) {
      return tokens.stream()
          .filter(it -> type.equalsIgnoreCase(it.name()))
          .findFirst()
          .orElseThrow();
    }
  }

  private String generate(String userId, TokenType tokenType) {
    final long now = Instant.now().getEpochSecond();
    return JWT.create()
        .withClaim(UID, userId)
        .withClaim(TOKEN_TYPE, tokenType.name())
        .withClaim(IAT, now)
        .withClaim(EXP, now + getLifeTime(tokenType))
        .sign(AL);
  }

  public String issueAccessToken(String userId) {
    return generate(userId, TokenType.ACCESS);
  }

  public String issueRefreshToken(String userId) {
    return generate(userId, TokenType.REFRESH);
  }

  public boolean isValidToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(AL)
          .build();
      verifier.verify(token);
      return true;
    } catch (JWTVerificationException exception){
      return false;
    }
  }

  public String parseUserId(String token) {
    return JWT.decode(token).getClaim(UID).asString();
  }

  private long getLifeTime(TokenType tokenType) {
    return switch (tokenType) {
      case ACCESS -> this.jwtProperties.accessTimeSec();
      default -> this.jwtProperties.refreshTimeSec();
    };
  }

  public String parseTokenFromHeader(HttpServletRequest httpServletRequest) {
    final String authorization = httpServletRequest.getHeader("Authorization");
    if (Objects.isNull(authorization) || !authorization.startsWith("Bearer")) {
      throw new IllegalStateException();
    }
    return authorization.substring(7);
  }
}
