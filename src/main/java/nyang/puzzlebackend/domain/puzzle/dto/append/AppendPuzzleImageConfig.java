package nyang.puzzlebackend.domain.puzzle.dto.append;

import lombok.Builder;

@Builder
public class AppendPuzzleImageConfig {
  private Long width;
  private Long height;

  public static AppendPuzzleImageConfig from() {
    return AppendPuzzleImageConfig.builder()

        .build();
  }
}
