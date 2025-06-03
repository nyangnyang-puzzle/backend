package nyang.puzzlebackend.api.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ImageUrlValidator implements ConstraintValidator<ImageUrl, String> {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    @Override
    public boolean isValid(String urlString, ConstraintValidatorContext context) {
        if (urlString == null) {
            return false;
        }

        try {
            URL url = new URL(urlString);
            String path = url.getPath();
            String extension = path.substring(path.lastIndexOf(".") + 1).toLowerCase();

            return ALLOWED_EXTENSIONS.contains(extension);
        } catch (Exception e) {
            return false;
        }
    }
}