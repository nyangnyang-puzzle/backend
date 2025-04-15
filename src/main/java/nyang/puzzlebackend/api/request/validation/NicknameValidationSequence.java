package nyang.puzzlebackend.api.request.validation;

import jakarta.validation.GroupSequence;
import jakarta.validation.groups.Default;
import nyang.puzzlebackend.api.request.validation.ValidationGroups.NotBlankGroup;
import nyang.puzzlebackend.api.request.validation.ValidationGroups.NotWhiteSpaceGroup;
import nyang.puzzlebackend.api.request.validation.ValidationGroups.SizeGroup;

@GroupSequence({Default.class, NotBlankGroup.class, NotWhiteSpaceGroup.class, SizeGroup.class})
public interface NicknameValidationSequence {

}