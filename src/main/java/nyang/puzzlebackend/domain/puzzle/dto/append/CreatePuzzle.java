package nyang.puzzlebackend.domain.puzzle.dto.append;

import lombok.Builder;
import nyang.puzzlebackend.api.request.puzzle.PuzzleCreateRequest;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.domain.puzzle.model.Puzzle;

@Builder
public class CreatePuzzle {

  private CreatePuzzleConfig appendConfig;
  private int level;
  private String title;
  private boolean secretRoom;
  private int maximumPlayer;
  private int perfection;
  private String thumbImage;

  public static CreatePuzzle from(PuzzleCreateRequest puzzleCreateRequest) {
    return CreatePuzzle.builder()
        .appendConfig(CreatePuzzleConfig.from(puzzleCreateRequest.getConfig()))
        .level(puzzleCreateRequest.getLevel())
        .title(puzzleCreateRequest.getTitle())
        .secretRoom(puzzleCreateRequest.isSecretRoom())
        .maximumPlayer(puzzleCreateRequest.getMaximumPlayer())
        .perfection(puzzleCreateRequest.getPerfection())
        .thumbImage(puzzleCreateRequest.getThumbImage())
        .build();
  }

  public Puzzle toPuzzle(AppUser appUser) {
    return Puzzle.builder()
        .config(appendConfig.toConfig())
        .ownerId(appUser.uid())
        .level(level)
        .title(title)
        .secretRoom(secretRoom)
        .maximumPlayer(maximumPlayer)
        .perfection(perfection)
        .thumbImage(thumbImage)
        .build();
  }
}
