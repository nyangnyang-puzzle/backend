package nyang.puzzlebackend.auth.oauth.exception;

public class OAuthProcessingException extends RuntimeException {
    public OAuthProcessingException(String message) {
        super(message);
    }

    public OAuthProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
} 