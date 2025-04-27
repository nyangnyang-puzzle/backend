package nyang.puzzlebackend.api.request.puzzle;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PuzzleCreateRequest {

  private RequestPuzzleConfig config;
  private int level;
  private String title;
  private boolean secretRoom;
  private int maximumPlayer;
  private int perfection;
  private String thumbImage;
}
