package nyang.puzzlebackend.global.error;

import lombok.Getter;

@Getter
public class PuzzleException extends RuntimeException{

    private final ErrorCode errorCode;

    public PuzzleException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
