package nyang.puzzlebackend.api.response.puzzle;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import java.time.LocalDateTime;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.domain.puzzle.model.Puzzle;

public record PuzzleItem(
  String id,
  String src,
  String thumbnail,
  boolean isCompleted,
  int pieceCount,
  boolean isOwner,
  PuzzleType type,

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  LocalDateTime createdAt
) {

  private static PuzzleItem.Builder builder(Puzzle puzzle) {
    return new Builder()
      .id(puzzle.getId().toString())
      .src(puzzle.getThumbImage())
      .thumbnail(puzzle.getThumbImage())
      .isCompleted(puzzle.getPerfection() != 0)
      .pieceCount(puzzle.getLevel())
      .type(PuzzleType.from(puzzle.isSecretRoom()))
      .createdAt(puzzle.getCreatedAt());
  }

  public static PuzzleItem of(Puzzle puzzle) {
    return builder(puzzle)
      .isOwner(false)
      .build();
  }

  public static PuzzleItem withUser(Puzzle puzzle, @Nullable AppUser appUser) {
    boolean owner = false;
    if (appUser != null) {
      owner = puzzle.getOwnerId().equals(appUser.uid());
    }
    return builder(puzzle)
      .isOwner(owner)
      .build();
  }

  public static class Builder {
    private String id;
    private String src;
    private String thumbnail;
    private boolean isCompleted;
    private int pieceCount;
    private boolean isOwner;
    private PuzzleType type;
    private LocalDateTime createdAt;

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder src(String src) {
      this.src = src;
      return this;
    }

    public Builder thumbnail(String thumbnail) {
      this.thumbnail = thumbnail;
      return this;
    }

    public Builder isCompleted(boolean isCompleted) {
      this.isCompleted = isCompleted;
      return this;
    }

    public Builder pieceCount(int pieceCount) {
      this.pieceCount = pieceCount;
      return this;
    }

    public Builder isOwner(boolean isOwner) {
      this.isOwner = isOwner;
      return this;
    }

    public Builder type(PuzzleType type) {
      this.type = type;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public PuzzleItem build() {
      return new PuzzleItem(id, src, thumbnail, isCompleted, pieceCount, isOwner, type, createdAt);
    }
  }

}
