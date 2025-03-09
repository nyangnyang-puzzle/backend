package nyang.puzzlebackend.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import nyang.puzzlebackend.global.error.ErrorContent;

@JsonInclude(value = Include.NON_NULL)
public record ApiResponse<T>(
    String result,
    T data,
    ErrorContent error
) {

  public static <T> ApiResponse<T> success() {
    return new ApiResponse<>("SUCCESS", null, null);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>("SUCCESS", data, null);
  }

  public static <T> ApiResponse<T> error(ErrorContent errorContent) {
    return new ApiResponse<>("ERROR", null, errorContent);
  }
}