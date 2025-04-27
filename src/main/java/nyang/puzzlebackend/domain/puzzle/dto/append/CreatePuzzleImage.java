package nyang.puzzlebackend.domain.puzzle.dto.append;

import lombok.Builder;
import nyang.puzzlebackend.api.request.puzzle.RequestPuzzleImage;
import nyang.puzzlebackend.domain.puzzle.model.PuzzleImage;

@Builder
public class CreatePuzzleImage {
  private String src;
  private Long width;
  private Long height;

  public static CreatePuzzleImage from(RequestPuzzleImage requestPuzzleImage) {
    return CreatePuzzleImage.builder()
        .src(requestPuzzleImage.getSrc())
        .height(requestPuzzleImage.getHeight())
        .width(requestPuzzleImage.getWidth())
        .build();
  }

  public PuzzleImage toPuzzleImage() {
    return PuzzleImage.builder()
        .src(src)
        .width(width)
        .height(height)
        .build();
  }
}
