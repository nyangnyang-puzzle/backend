package nyang.puzzlebackend.domain.puzzle.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PuzzleGroupPoint {
  private double x;
  private double y;
  private Integer groupIndex;
}
