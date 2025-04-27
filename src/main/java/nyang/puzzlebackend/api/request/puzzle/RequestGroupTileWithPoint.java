package nyang.puzzlebackend.api.request.puzzle;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestGroupTileWithPoint {
  private double x;
  private double y;
  private Integer groupIndex;
}
