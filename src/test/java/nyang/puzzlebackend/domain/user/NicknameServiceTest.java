package nyang.puzzlebackend.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class NicknameServiceTest {

  @Mock
  private RedisTemplate<String, String> redisTemplate;

  private NicknameService nicknameService;

  @BeforeEach
  void setUp() {
    nicknameService = new NicknameService(redisTemplate);
  }

  @Test
   void checkAvailableNickname() {

    // Given
    String nickname = "existingNickname";
    when(redisTemplate.hasKey(nickname)).thenReturn(true);

    // When
    boolean result = nicknameService.checkAvailableNickname(nickname);

    // Then
    assertThat(result).isTrue();
  }
}