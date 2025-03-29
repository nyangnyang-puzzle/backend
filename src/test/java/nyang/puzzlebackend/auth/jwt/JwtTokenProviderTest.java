package nyang.puzzlebackend.auth.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

@DisplayName("JWT 토큰 발행기 테스트")
class JwtTokenProviderTest {

  private JwtTokenProvider jwtTokenProvider;
  private JwtProperties jwtProperties;
  private String userId;
  private final long givenAccessTimeSec = 1L;
  private final long givenRefreshTimeSec = 2L;
  private final String givenSecret = "test-secret";

  @BeforeEach
  void setUp() {
    jwtProperties = mock(JwtProperties.class);
    when(jwtProperties.secret()).thenReturn(givenSecret);
    when(jwtProperties.accessTimeSec()).thenReturn(givenAccessTimeSec);
    when(jwtProperties.refreshTimeSec()).thenReturn(givenRefreshTimeSec);
    jwtTokenProvider = new JwtTokenProvider(jwtProperties);
    userId = "5f9b1f7b9b9b9b9b9b9b9b9b";
  }

  @Test
  @DisplayName("액세스 토큰 발급 및 유효성 검증")
  void issueAccessToken_ValidUserId_ReturnsValidToken() {
    final String accessToken = jwtTokenProvider.issueAccessToken(userId);
    assertThat(jwtTokenProvider.isValidToken(accessToken)).isTrue();
  }

  @Test
  @DisplayName("리프레시 토큰 발급 및 유효성 검증")
  void issueRefreshToken_ValidUserId_ReturnsValidToken() {
    final String refreshToken = jwtTokenProvider.issueRefreshToken(userId);
    assertThat(jwtTokenProvider.isValidToken(refreshToken)).isTrue();
  }

  @Test
  @DisplayName("유효 하지 않은 토큰 검증")
  void isValidToken_InvalidToken_ReturnsFalse() {
    assertThat(jwtTokenProvider.isValidToken("invalid-token")).isFalse();
  }

  @Test
  @DisplayName("유효한 토큰에서 사용자 ID 추출")
  void parseUserId_ValidToken_ReturnsUserId() {
    String accessToken = jwtTokenProvider.issueAccessToken(userId);
    String parsedUserId = jwtTokenProvider.parseUserId(accessToken);
    assertThat(parsedUserId).isEqualTo(userId);
  }

  @Test
  @DisplayName("TokenLifeTime 이후 발행한 엑세스 토큰 은 유효 하지 않다.")
  void accessTokenExpiredAfterIssuedTokenLifeTime() throws InterruptedException {
    String accessToken = jwtTokenProvider.issueAccessToken(userId);
    Thread.sleep(givenAccessTimeSec * 1000 + 1L);

    // when
    boolean validToken = jwtTokenProvider.isValidToken(accessToken);

    // then
    assertThat(validToken).isFalse();
  }

  @Test
  @DisplayName("TokenLifeTime 이후 발행한 리프레시 토큰은 유효 하지 않다.")
  void refreshTokenExpiredAfterIssuedTokenLifeTime() throws InterruptedException {
    String refreshToken = jwtTokenProvider.issueRefreshToken(userId);
    Thread.sleep(givenRefreshTimeSec * 1000 + 1L);

    // when
    boolean validToken = jwtTokenProvider.isValidToken(refreshToken);

    // then
    assertThat(validToken).isFalse();
  }

  @Test
  @DisplayName("요청 헤더 에서 Token 을 파싱 한다.")
  void parseTokenFromHeader() {
    String accessToken = jwtTokenProvider.issueAccessToken(userId);

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer " + accessToken);

    String parsedToken = jwtTokenProvider.parseTokenFromHeader(request);

    assertThat(parsedToken).isEqualTo(accessToken);
  }

  @Test
  @DisplayName("요청 헤더에서 값이 없는 경우 예외를 던진다.")
  void throwExceptionWhenEmptyOrWrongTokenInHeader() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bear");

    assertThatThrownBy(() -> jwtTokenProvider.parseTokenFromHeader(request))
        .isInstanceOf(IllegalStateException.class);
  }
}