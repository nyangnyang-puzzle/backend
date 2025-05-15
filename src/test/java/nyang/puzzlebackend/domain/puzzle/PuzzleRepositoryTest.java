package nyang.puzzlebackend.domain.puzzle;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.ArrayList;
import nyang.puzzlebackend.domain.puzzle.model.CanvasSize;
import nyang.puzzlebackend.domain.puzzle.model.Config;
import nyang.puzzlebackend.domain.puzzle.model.Puzzle;
import nyang.puzzlebackend.domain.puzzle.model.PuzzleImage;
import nyang.puzzlebackend.domain.puzzle.model.QPuzzle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@DataMongoTest
class PuzzleRepositoryTest {

  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

  @DynamicPropertySource
  static void containersProperties(DynamicPropertyRegistry registry) {
    mongoDBContainer.start();
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  private Puzzle puzzle1;

  @Autowired
  PuzzleRepository repository;

  @BeforeEach
  void setUp() {
    repository.deleteAll();

    var config = Config.builder()
      .shapes(new ArrayList<>())
      .imgWidth(800.0)
      .imgHeight(600.0)
      .tilesPerColumn(10)
      .tilesPerRow(10)
      .tileWidth(80.0)
      .tileHeight(60.0)
      .puzzleImage(PuzzleImage.builder()
        .src("1")
        .height(1L)
        .width(1L)
        .build())
      .groupTiles(new ArrayList<>())
      .groupCheck(false)
      .canvasSize(new CanvasSize())
      .canvasPreSize(new CanvasSize())
      .complete(false)
      .pieceCount(100)
      .build();

    var puzzle = Puzzle.builder()
      .config(config)
      .ownerId("owner123")
      .level(1)
      .title("Test Puzzle")
      .secretRoom(false)
      .maximumPlayer(4)
      .perfection(100)
      .thumbImage("http://example.com/thumb.jpg")
      .build();

    puzzle1 = repository.save(puzzle);
  }

  @Test
  void findAllByPredicate() {
    assertThat(repository.findAll(QPuzzle.puzzle.isDeleted.isFalse())).contains(puzzle1);
  }
}