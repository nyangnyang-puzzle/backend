package nyang.puzzlebackend.image;

public record PresignedUrlResponse(
  String uploadUrl,
  String objectKey,
  String retrieveUrl
) { }