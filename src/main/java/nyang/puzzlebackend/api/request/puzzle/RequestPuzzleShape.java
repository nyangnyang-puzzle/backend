package nyang.puzzlebackend.api.request.puzzle;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class RequestPuzzleShape {
      private long topTab;
      private long rightTab;
      private long bottomTab;
      private long leftTab;
}