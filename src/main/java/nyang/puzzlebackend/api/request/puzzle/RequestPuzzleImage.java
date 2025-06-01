package nyang.puzzlebackend.api.request.puzzle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPuzzleImage {
  private String src;
  private Long width;
  private Long height;
}