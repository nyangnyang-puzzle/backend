package nyang.puzzlebackend.api.common;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

import java.time.Instant;
import nyang.puzzlebackend.global.error.ErrorContent;
import nyang.puzzlebackend.global.error.ErrorContents;

public record ApiResponse<T>(
    String result,
    T data,
    String timestamp,
    ErrorContent error,
    ErrorContents errors
) {

  private ApiResponse(String result, T data) {
    this(result, data, ISO_INSTANT.format(Instant.now()), null, null);
  }

  private ApiResponse(String result, ErrorContent error) {
    this(result, null, ISO_INSTANT.format(Instant.now()), error, null);
  }

  private ApiResponse(String result, ErrorContents errors) {
    this(result, null, ISO_INSTANT.format(Instant.now()), null, errors);
  }

  public static <T> ApiResponse<T> ok(T data) {
    return new ApiResponse<>("success", data);
  }

  public static <T> ApiResponse<T> ok() {
    return new ApiResponse<>("success", null, ISO_INSTANT.format(Instant.now()), null, null);
  }

  public static <T> ApiResponse<T> error(ErrorContent errorContent) {
    return new ApiResponse<>("error", errorContent);
  }

  public static <T> ApiResponse<T> errors(ErrorContents errorContent) {
    return new ApiResponse<>("error", errorContent);
  }
}