package nyang.puzzlebackend.api.request.puzzle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestGroupTileWithPoint {
  private double x;
  private double y;
  private Integer groupIndex;
}
