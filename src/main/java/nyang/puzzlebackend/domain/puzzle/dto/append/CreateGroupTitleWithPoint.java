package nyang.puzzlebackend.domain.puzzle.dto.append;

import lombok.Builder;
import lombok.Data;
import nyang.puzzlebackend.api.request.puzzle.RequestGroupTileWithPoint;
import nyang.puzzlebackend.domain.puzzle.model.PuzzleGroupPoint;

@Data
@Builder
public class CreateGroupTitleWithPoint {
  private double x;
  private double y;
  private Integer groupIndex;

  public static CreateGroupTitleWithPoint from(RequestGroupTileWithPoint point) {
    return CreateGroupTitleWithPoint.builder()
        .x(point.getX())
        .y(point.getY())
        .groupIndex(point.getGroupIndex())
        .build();
  }

  public PuzzleGroupPoint toGroupPoint () {
    return PuzzleGroupPoint.builder()
        .x(x)
        .y(y)
        .groupIndex(groupIndex)
        .build();
  }
}
