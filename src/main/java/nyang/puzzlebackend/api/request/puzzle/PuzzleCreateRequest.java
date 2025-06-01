package nyang.puzzlebackend.api.request.puzzle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
