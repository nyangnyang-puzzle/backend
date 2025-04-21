package nyang.puzzlebackend.global.error;

public class AuthenticationException extends PuzzleException{

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
