package nyang.puzzlebackend.domain.user;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class NicknameService {

  private final RedisTemplate<String, String> redisTemplate;

  public NicknameService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public boolean checkAvailableNickname(String nickname) {
    return !redisTemplate.hasKey(nickname);
  }
}
