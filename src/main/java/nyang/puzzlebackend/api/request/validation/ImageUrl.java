package nyang.puzzlebackend.api.request.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import nyang.puzzlebackend.api.request.ImageUrlValidator;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageUrlValidator.class)
public @interface ImageUrl {
    String message() default "Invalid image URL";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}