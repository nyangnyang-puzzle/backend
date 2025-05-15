package nyang.puzzlebackend.api.response.puzzle;

public enum PuzzleType {
  PUBLIC(false), PRIVATE(true);

  private boolean secretRoom;

  PuzzleType(boolean secretRoom) {
    this.secretRoom = secretRoom;
  }

  public static PuzzleType from(boolean isSecretRoom) {
    return isSecretRoom ? PRIVATE : PUBLIC;
  }
}
