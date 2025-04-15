package nyang.puzzlebackend.api.request;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import nyang.puzzlebackend.api.request.validation.NicknameValidationSequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NicknameRequestTest {

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    validator = factory.getValidator();
  }

  @Test
  void validNickname_shouldPassValidation() {
    NicknameRequest request = new NicknameRequest("validNickname");

    var violations = validator.validate(request, NicknameValidationSequence.class);

    assertThat(violations).isEmpty();

  }

  @Test
  void emptyNickname_shouldFailValidation() {
    NicknameRequest request = new NicknameRequest("");

    var violations = validator.validate(request, NicknameValidationSequence.class);
    assertThat(violations).isNotEmpty();
  }

  @Test
  void shortNickname_shouldFailValidation() {
    NicknameRequest request = new NicknameRequest("ab");

    var violations = validator.validate(request, NicknameValidationSequence.class);
    assertThat(violations).isNotEmpty();
  }

  @Test
  void longNickname_shouldFailValidation() {
    NicknameRequest request = new NicknameRequest("thisNicknameIsWayTooLong");

    var violations = validator.validate(request, NicknameValidationSequence.class);
    assertThat(violations).isNotEmpty();
  }
}