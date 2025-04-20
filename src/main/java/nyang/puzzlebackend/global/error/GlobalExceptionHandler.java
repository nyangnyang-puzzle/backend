package nyang.puzzlebackend.global.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.api.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PuzzleException.class)
    public ResponseEntity<ApiResponse<ErrorContent>> handleAppException(HttpServletRequest req, PuzzleException e) {
        ErrorCode ec = e.getErrorCode();
        log.debug("[potenday exception] errCode = {}, message = {}, status = {}, instance = {}",
            ec.getCode(), ec.getMessage(), ec.getHttpStatus().value(), req.getRequestURI());
        return ResponseEntity.status(ec.getHttpStatus()).body(ApiResponse.error(ErrorContent.from(ec)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorContent>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        ErrorContents errorContents = new ErrorContents(e.getFieldErrors());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.errors(errorContents));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<ErrorContent>> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException e
    ) {
        log.warn(e.getMessage());
        return ResponseEntity.status(ErrorCode.X002.getHttpStatus())
            .body(ApiResponse.error(ErrorContent.from(ErrorCode.X002)));
    }
}
