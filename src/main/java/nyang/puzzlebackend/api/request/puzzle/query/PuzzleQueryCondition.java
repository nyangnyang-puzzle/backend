package nyang.puzzlebackend.api.request.puzzle.query;

import lombok.Getter;
import nyang.puzzlebackend.api.response.puzzle.PuzzleType;

@Getter
public class PuzzleQueryCondition {
  PuzzleType ptype;
  Boolean completed;

  public PuzzleQueryCondition(PuzzleType ptype, Boolean completed) {
    this.ptype = ptype;
    this.completed = completed;
  }

  @Override
  public String toString() {
    return "PuzzleQueryCondition{" +
      "ptype=" + ptype +
      ", isCompleted=" + completed +
      '}';
  }
}
