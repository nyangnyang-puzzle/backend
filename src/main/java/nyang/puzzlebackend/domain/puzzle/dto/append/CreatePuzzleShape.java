package nyang.puzzlebackend.domain.puzzle.dto.append;

import lombok.Builder;
import nyang.puzzlebackend.api.request.puzzle.RequestPuzzleShape;
import nyang.puzzlebackend.domain.puzzle.model.Shape;

@Builder
public class CreatePuzzleShape {

  private long topTab;
  private long rightTab;
  private long bottomTab;
  private long leftTab;

  public static CreatePuzzleShape from(RequestPuzzleShape shape) {
    return CreatePuzzleShape.builder()
        .topTab(shape.getTopTab())
        .rightTab(shape.getRightTab())
        .bottomTab(shape.getBottomTab())
        .leftTab(shape.getLeftTab())
        .build();
  }

  public Shape toShape() {
    return Shape.builder()
        .topTab(topTab)
        .rightTab(rightTab)
        .bottomTab(bottomTab)
        .leftTab(leftTab)
        .build();
  }

}
