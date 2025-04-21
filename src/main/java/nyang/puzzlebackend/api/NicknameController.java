package nyang.puzzlebackend.api;

import lombok.RequiredArgsConstructor;
import nyang.puzzlebackend.api.common.ApiResponse;
import nyang.puzzlebackend.api.request.NicknameRequest;
import nyang.puzzlebackend.api.request.validation.NicknameValidationSequence;
import nyang.puzzlebackend.api.response.user.NicknameAvailableResponse;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.auth.AuthPrincipal;
import nyang.puzzlebackend.domain.user.NicknameService;
import nyang.puzzlebackend.domain.user.UserInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NicknameController {

  private final NicknameService nicknameService;
  private final UserInfoService userInfoService;

  @PostMapping("/user/nickname/available-check")
  public ApiResponse<NicknameAvailableResponse> checkNicknameDuplicated(
      @Validated(value = NicknameValidationSequence.class) @RequestBody NicknameRequest nicknameRequest
  ) {
    boolean isAvailable = nicknameService.checkAvailableNickname(nicknameRequest.nickname());
    return ApiResponse.ok(new NicknameAvailableResponse(isAvailable));
  }

  @PostMapping("/user/nickname")
  public ApiResponse<?> register(
      @Validated(value = NicknameValidationSequence.class)
      @RequestBody NicknameRequest nicknameRequest,
      @AuthPrincipal AppUser appUser
  ) {
    userInfoService.updateNickname(nicknameRequest.nickname(), appUser);
    return ApiResponse.ok();
  }
}
