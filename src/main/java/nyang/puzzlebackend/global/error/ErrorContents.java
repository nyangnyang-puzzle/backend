package nyang.puzzlebackend.global.error;

import java.util.List;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ErrorContents {

    private final List<ErrorContent> contents;

    public ErrorContents(List<FieldError> fieldErrors) {
        this.contents = fieldErrors.stream()
            .map(it -> ErrorContent.from(ErrorCode.of(it.getDefaultMessage())))
            .toList();
    }
}
