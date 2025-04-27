package nyang.puzzlebackend.domain.puzzle.model;

import lombok.Builder;

@Builder
public class Shape {

    private long topTab;
    private long rightTab;
    private long bottomTab;
    private long leftTab;

//    public static Shape fromRequestShape(RequestShape requestShape) {
//      return Shape.builder()
//          .topTab(requestShape.getTopTab())
//          .rightTab(requestShape.getRightTab())
//          .bottomTab(requestShape.getBottomTab())
//          .leftTab(requestShape.getLeftTab())
//          .build();
//    }
  }