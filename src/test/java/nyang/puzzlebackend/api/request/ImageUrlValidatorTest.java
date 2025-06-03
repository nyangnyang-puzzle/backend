package nyang.puzzlebackend.api.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ImageUrlValidatorTest {
    private ImageUrlValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ImageUrlValidator();
    }

    @Test
    void validImageUrl_jpg_shouldReturnTrue() {
        assertTrue(validator.isValid("https://dev-puzzle-api.nyangnyang.co.kr/images/original/test.jpg", null));
    }

    @Test
    void validImageUrl_png_shouldReturnTrue() {
        assertTrue(validator.isValid("https://dev-puzzle-api.nyangnyang.co.kr/images/original/test.png", null));
    }

    @Test
    void invalidImageUrl_txt_shouldReturnFalse() {
        assertFalse(validator.isValid("https://dev-puzzle-api.nyangnyang.co.kr/images/original/test.txt", null));
    }

    @Test
    void invalidImageUrl_malformed_shouldReturnFalse() {
        assertFalse(validator.isValid("not-a-url", null));
    }

    @Test
    void nullImageUrl_shouldReturnFalse() {
        assertFalse(validator.isValid(null, null));
    }
} 