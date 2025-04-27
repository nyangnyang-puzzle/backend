package nyang.puzzlebackend.domain.puzzle.model;

import java.util.List;
import lombok.Builder;

@Builder
public class Config {
    private List<Shape> shapes;
    private double imgWidth;
    private double imgHeight;
    private long tilesPerColumn;
    private long tilesPerRow;
    private double tileWidth;
    private double tileHeight;
    private PuzzleImage puzzleImage;
    private List<PuzzleGroupPoint> groupTiles;
    private boolean groupCheck;
    private CanvasSize canvasSize;
    private CanvasSize canvasPreSize;
    private boolean complete;
    private int pieceCount;
}