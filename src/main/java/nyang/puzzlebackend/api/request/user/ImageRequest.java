package nyang.puzzlebackend.api.request.user;

import jakarta.validation.constraints.NotNull;
import nyang.puzzlebackend.api.request.validation.ImageUrl;

public record ImageRequest(
  @NotNull(message = "U006")
  @ImageUrl(message = "U007")
  String imageUrl
) {}
