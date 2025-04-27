package nyang.puzzlebackend.domain.puzzle.model;

import lombok.Builder;
import nyang.puzzlebackend.api.request.puzzle.RequestPuzzleImage;

@Builder
public class PuzzleImage {

      private String src;
      private Long width;
      private Long height;

      public static PuzzleImage fromRequestPuzzleImage(RequestPuzzleImage requestPuzzleImage) {
            return PuzzleImage.builder()
                .src(requestPuzzleImage.getSrc())
                .width(requestPuzzleImage.getWidth())
                .height(requestPuzzleImage.getHeight())
                .build();
      }
}