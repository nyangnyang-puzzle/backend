package nyang.puzzlebackend.domain.puzzle.dto.append;

import lombok.Builder;
import nyang.puzzlebackend.api.request.puzzle.RequestCanvasSize;
import nyang.puzzlebackend.domain.puzzle.model.CanvasSize;

@Builder
public class CreatePuzzleCanvasSize {
  private Long width;
  private Long height;

  public static CreatePuzzleCanvasSize from(RequestCanvasSize canvasSize) {
    return CreatePuzzleCanvasSize.builder()
        .width(canvasSize.getWidth())
        .height(canvasSize.getHeight())
        .build();
  }

  public CanvasSize toCanvasSize() {
    return null;
  }
}
