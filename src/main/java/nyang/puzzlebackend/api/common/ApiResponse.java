package nyang.puzzlebackend.api.common;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.Instant;
import nyang.puzzlebackend.global.error.ErrorContent;

@JsonInclude(value = Include.NON_NULL)
public record ApiResponse<T>(
    String result,
    T data,
    String timestamp,
    ErrorContent error
) {

  private ApiResponse(String result, T data) {
    this(result, data, ISO_INSTANT.format(Instant.now()), null);
  }

  private ApiResponse(String result, ErrorContent error) {
    this(result, null, ISO_INSTANT.format(Instant.now()), error);
  }

  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>("succss", data);
  }

  public static <T> ApiResponse<T> error(ErrorContent errorContent) {
    return new ApiResponse<>("error", errorContent);
  }
}