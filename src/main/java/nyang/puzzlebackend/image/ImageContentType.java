package nyang.puzzlebackend.image;

import java.util.Arrays;
import java.util.List;
import nyang.puzzlebackend.global.error.ErrorCode;
import nyang.puzzlebackend.global.error.PuzzleException;

public enum ImageContentType {
  JPG("image/jpeg"),
  JPEG("image/jpeg"),
  PNG("image/png"),
  BMP("image/bmp"),
  WEBP("image/webp"),
  TIFF("image/tiff"),
  TIF("image/tiff"),
  SVG("image/svg+xml");

  private final String mimeType;

  private static final List<ImageContentType> contentTypes = Arrays.stream(ImageContentType.values()).toList();

  ImageContentType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String getMimeType() {
    return mimeType;
  }

  public static ImageContentType from(String fileExtension) {
    if (fileExtension == null || fileExtension.trim().isEmpty()) {
      throw new PuzzleException(ErrorCode.I001);
    }
    return contentTypes.stream()
      .filter(contentType -> fileExtension.equalsIgnoreCase(contentType.name()))
      .findFirst()
      .orElseThrow(() -> new PuzzleException(ErrorCode.I001));
  }
}
