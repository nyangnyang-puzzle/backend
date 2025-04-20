package nyang.puzzlebackend.global.error;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  // 유저 닉네임
  U001("UN001", "닉네임은 3 ~ 15자 사이 이어야 합니다.", HttpStatus.CONFLICT),
  U002("UN002", "닉네임에는 공백이 있을 수 없습니다.", HttpStatus.BAD_REQUEST),
  U003("UN003", "닉네임을 입력하기 바랍니다.", HttpStatus.BAD_REQUEST),

  // Uncaught Exception
  X001("X001", "서버에 문제가 발생 하였습니다. 관리자에게 연락해주세요", HttpStatus.INTERNAL_SERVER_ERROR),
  X002("X002", "잘못된 형식의 요청입니다.", HttpStatus.BAD_REQUEST);

  private final String code;
  private final String message;
  private final HttpStatus httpStatus;

  private static final List<ErrorCode> errorCodes = Arrays.stream(ErrorCode.values()).toList();

  ErrorCode(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }

  public static ErrorCode of(String code) {
    return errorCodes.stream()
        .filter(errorCode -> errorCode.getCode().equals(code))
        .findFirst()
        .orElse(X001);
  }
}