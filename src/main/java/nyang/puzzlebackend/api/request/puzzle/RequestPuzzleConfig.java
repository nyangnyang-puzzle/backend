package nyang.puzzlebackend.api.request.puzzle;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPuzzleConfig {
    private List<RequestPuzzleShape> shapes;
    private double imgWidth;
    private double imgHeight;
    private long tilesPerColumn;
    private long tilesPerRow;
    private double tileWidth;
    private double tileHeight;
    private RequestPuzzleImage puzzleImage;
    private List<RequestGroupTileWithPoint> groupTiles;
    private boolean groupCheck;
    private RequestCanvasSize canvasSize;
    private RequestCanvasSize canvasPreSize;
    private boolean complete;
    private int pieceCount;
  }