package nyang.puzzlebackend.oauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import nyang.puzzlebackend.auth.oauth.OAuthTokenRequest;
import nyang.puzzlebackend.auth.oauth.OAuthClient;
import nyang.puzzlebackend.auth.oauth.OAuthToken;
import nyang.puzzlebackend.auth.oauth.OAuthMember;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OAuthExampleTest {

  private static final String REDIRECT_URI = "redirect-uri";
  /**
   * oAuthAuthorization -> code -> token 요청 ( 공통 )
   * token -> provider 에게 유저 정보 요정 -> 유저 고유 id or email
   * 유저 id 와 email 가입 여부 확인
   * 가입 안되어 있으면 고유 id 로 가입( 필요하면 email 도) (이떄 가입 되어 있으면 새로운 rt, at 발급)
   * 이후 jwt 토큰 발행과 동시에 유저 아이디를 key, value 로 refresh list 에 추가.
   * 이후 응답하기
   */

  @Test
  void testOAuthLogin() {
    // Mock 객체 생성
    OAuthClient oAuthClient = Mockito.mock(OAuthClient.class);
    AppTokenProvider appTokenProvider = Mockito.mock(AppTokenProvider.class);

    // Mock 객체 동작 정의
    OAuthTokenRequest oauthTokenRequest = new OAuthTokenRequest("1", "1", "1", "1");
    OAuthToken oAuthToken = new OAuthToken("access-token", "refresh-token", "token-type", "expires-in");
    OAuthMember oAuthMember = new OAuthMember("user-id", "user-email");
    User user = new User(oAuthMember);
    AppToken appToken = new AppToken();

    when(oAuthClient.getAccessToken(oauthTokenRequest)).thenReturn(oAuthToken);
    when(oAuthClient.getUserProfile(oAuthToken)).thenReturn(oAuthMember);
    when(appTokenProvider.issueAppToken(user)).thenReturn(appToken);

    // 테스트 코드 작성
    UserRepository userRepository = new UserRepository();
    UserAccountService userAccountService = new UserAccountService(userRepository);
    OAuthToken oAuthToken1 = oAuthClient.getAccessToken(oauthTokenRequest);
    OAuthMember oAuthMember1 = oAuthClient.getUserProfile(oAuthToken1);
    User user1 = userAccountService.findOrSaveUser(oAuthMember1);
    AppToken appToken1 = appTokenProvider.issueAppToken(user1);

    // 검증
    Mockito.verify(oAuthClient).getAccessToken(oauthTokenRequest);
    Mockito.verify(oAuthClient).getUserProfile(oAuthToken);
    Mockito.verify(appTokenProvider).issueAppToken(user);

    assertThat(appToken1).isNotNull();
  }

  private interface AppTokenProvider {

    // 발급 + 저장 까지 보는 게 하나의 issue 과정 으로 볼것인 지 ?
    // 이렇게 하면 테스트 가 어렵지 않을까?
    AppToken issueAppToken(User user); // 내부에 구현은 어떻게 될 지는 모르겠지만. 이렇게
  }

  public record UserProfile(
      String oauthId,
      String email
  ) { }

  private class UserAccountService {

    private final UserRepository userRepository;

    private UserAccountService(UserRepository userRepository) {
      this.userRepository = userRepository;
    }
    public User findOrSaveUser(OAuthMember userProfile) {
      Optional<User> user = userRepository.findOAuthId(userProfile);
      return user.orElseGet(() -> userRepository.save(new User(userProfile)));
    }
  }

  private class UserRepository {

    public Optional<User> findOAuthId(OAuthMember userProfile) {
      return Optional.of(new User(userProfile));
    }

    public User save(User user) {
      return user;
    }
  }

  private record User(
      OAuthMember oauthUserId
  ) { }

  private class AppToken {
    String accessToken;
    String refreshToken;
  }
}
