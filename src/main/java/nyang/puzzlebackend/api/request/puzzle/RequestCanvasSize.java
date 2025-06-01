package nyang.puzzlebackend.api.request.puzzle;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RequestCanvasSize {

  private Long width;
  private Long height;
}