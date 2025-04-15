package nyang.puzzlebackend.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import nyang.puzzlebackend.api.request.validation.ValidationGroups.NotBlankGroup;
import nyang.puzzlebackend.api.request.validation.ValidationGroups.NotWhiteSpaceGroup;
import nyang.puzzlebackend.api.request.validation.ValidationGroups.SizeGroup;


public record NicknameRequest(
    @NotBlank(message = "UN003", groups = NotBlankGroup.class)
    @Pattern(regexp = "\\S+", message = "UN002", groups = NotWhiteSpaceGroup.class)
    @Size(min = 3, max = 15, message = "UN001", groups = SizeGroup.class)
    String nickname
) { }
