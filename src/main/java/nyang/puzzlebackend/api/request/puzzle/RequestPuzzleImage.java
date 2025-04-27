package nyang.puzzlebackend.api.request.puzzle;

import lombok.Getter;

@Getter
public class RequestPuzzleImage {
  private String src;
  private Long width;
  private Long height;
}