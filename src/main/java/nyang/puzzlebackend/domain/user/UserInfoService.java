package nyang.puzzlebackend.domain.user;


import lombok.RequiredArgsConstructor;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.global.error.ErrorCode;
import nyang.puzzlebackend.global.error.PuzzleException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

  private final UserRepository userRepository;

  public void updateProfileImage(final String imgUrl, AppUser appUser) {
    User user = findUser(appUser);
    user.updateProfileImg(imgUrl);
    userRepository.save(user);
  }

  public void updateNickname(final String nickname, AppUser appUser) {
    User user = findUser(appUser);
    user.initNickname(nickname);
    userRepository.save(user);
  }

  private User findUser(AppUser appUser) {
    return userRepository.findById(new ObjectId(appUser.uid()))
        .orElseThrow(() -> new PuzzleException(ErrorCode.U004));
  }
}
