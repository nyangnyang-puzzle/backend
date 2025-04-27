package nyang.puzzlebackend.domain.puzzle.dto.append;

import java.util.List;
import lombok.Builder;
import nyang.puzzlebackend.api.request.puzzle.RequestPuzzleConfig;
import nyang.puzzlebackend.domain.puzzle.model.Config;

@Builder
public class CreatePuzzleConfig {

  private List<CreatePuzzleShape> shapes;
  private double imgWidth;
  private double imgHeight;
  private long tilesPerColumn;
  private long tilesPerRow;
  private double tileWidth;
  private double tileHeight;
  private CreatePuzzleImage createPuzzleImage;
  private List<CreateGroupTitleWithPoint> groupTiles;
  private boolean groupCheck;
  private CreatePuzzleCanvasSize appendCanvasSize;
  private CreatePuzzleCanvasSize appendCanvasPreSize;
  private boolean complete;
  private int pieceCount;

  public static CreatePuzzleConfig from(RequestPuzzleConfig requestConfig) {
    return CreatePuzzleConfig.builder()
        .shapes(requestConfig.getShapes()
            .stream()
            .map(CreatePuzzleShape::from)
            .toList())
        .imgWidth(requestConfig.getImgWidth())
        .imgHeight(requestConfig.getImgHeight())
        .tilesPerColumn(requestConfig.getTilesPerColumn())
        .tileHeight(requestConfig.getTileHeight())
        .tilesPerRow(requestConfig.getTilesPerRow())
        .imgWidth(requestConfig.getImgWidth())
        .imgHeight(requestConfig.getImgHeight())
        .createPuzzleImage(CreatePuzzleImage.from(requestConfig.getPuzzleImage()))
        .groupCheck(requestConfig.isGroupCheck())
        .appendCanvasSize(CreatePuzzleCanvasSize.from(requestConfig.getCanvasSize()))
        .appendCanvasPreSize(CreatePuzzleCanvasSize.from(requestConfig.getCanvasPreSize()))
        .complete(requestConfig.isComplete())
        .pieceCount(requestConfig.getPieceCount())
        .groupTiles(
            requestConfig.getGroupTiles()
                .stream()
                .map(CreateGroupTitleWithPoint::from)
                .toList())
        .build();
  }

  /**
   * private Double imgWidth; private Double imgHeight; private Long tilesPerColumn; private Long
   * tilesPerRow; private Double tileWidth; private Double tileHeight; private AppendPuzzleImage
   * puzzleImage; private List<List<Double>> groupTiles; private Boolean groupCheck; private
   * AppendPuzzleCanvasSize canvasSize; private AppendPuzzleCanvasSize canvasPreSize; private
   * Boolean complete; private int pieceCount;
   */
  public Config toConfig() {
    return Config.builder()
        .shapes(
            shapes.stream()
                .map(CreatePuzzleShape::toShape)
                .toList()
        )
        .groupTiles(
            groupTiles.stream()
                .map(CreateGroupTitleWithPoint::toGroupPoint)
                .toList()
        )
        .imgWidth(imgWidth)
        .imgHeight(imgHeight)
        .tilesPerColumn(tilesPerColumn)
        .tilesPerRow(tilesPerRow)
        .tileWidth(tileWidth)
        .tileHeight(tileHeight)
        .puzzleImage(createPuzzleImage.toPuzzleImage())
        .groupCheck(groupCheck)
        .canvasPreSize(appendCanvasPreSize.toCanvasSize())
        .canvasSize(appendCanvasSize.toCanvasSize())
        .complete(complete)
        .pieceCount(pieceCount)
        .build();
  }

}
