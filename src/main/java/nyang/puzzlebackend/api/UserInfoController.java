package nyang.puzzlebackend.api;

import jakarta.validation.Valid;
import nyang.puzzlebackend.api.common.ApiResponse;
import nyang.puzzlebackend.api.request.user.ImageRequest;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.auth.AuthPrincipal;
import nyang.puzzlebackend.domain.user.UserInfoService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class UserInfoController {

  private final UserInfoService userInfoService;

  public UserInfoController(UserInfoService userInfoService) {
    this.userInfoService = userInfoService;
  }

  @PatchMapping("/api/user/profile-img")
  public ApiResponse<?> changeProfileImg(
    @Valid @RequestBody ImageRequest imageRequest,
    @AuthPrincipal AppUser appUser
  ) {
    AppUser appUser1 = new AppUser("683c3d8f820a5135a5bf4828");
    userInfoService.updateProfileImage(imageRequest.imageUrl(), appUser1);
    return ApiResponse.ok();
  }
}
