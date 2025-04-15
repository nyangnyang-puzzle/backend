package nyang.puzzlebackend.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Import(RedisTestConfigTest.class)
public class UserNicknameRedisTest{

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  @Test
  public void test() {
    Boolean aBoolean = redisTemplate.hasKey("111");
    assertThat(aBoolean).isFalse();
  }
}
