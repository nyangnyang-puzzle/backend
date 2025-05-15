package nyang.puzzlebackend.domain.puzzle.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "puzzles")
public class Puzzle {

  @Id
  private ObjectId id;
  private Config config;

  @Field(name = "ownerId")
  private String ownerId;
  private int level;
  private String title;
  private boolean secretRoom;
  private int maximumPlayer;
  private int perfection;
  private String thumbImage;

  @Field(name = "deleted")
  private Boolean isDeleted;

  @Field(name = "createdAt")
  private LocalDateTime createdAt;

  @Builder
  public Puzzle(
      final Config config,
      final String ownerId,
      final int level,
      final String title,
      final boolean secretRoom,
      final int maximumPlayer,
      final int perfection,
      final String thumbImage
  ) {
    this.config = config;
    this.ownerId = ownerId;
    this.level = level;
    this.title = title;
    this.secretRoom = secretRoom;
    this.maximumPlayer = maximumPlayer;
    this.perfection = perfection;
    this.thumbImage = thumbImage;
    this.createdAt = LocalDateTime.now();
    this.isDeleted = false;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Puzzle puzzle)) {
      return false;
    }

    return id.equals(puzzle.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
