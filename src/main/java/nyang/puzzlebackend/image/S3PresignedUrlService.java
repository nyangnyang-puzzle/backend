package nyang.puzzlebackend.image;

import java.time.Duration;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class S3PresignedUrlService {

  @Value("${aws.s3.bucket-name}")
  private String bucketName;

  @Value("${aws.s3.retrieve-url}")
  private String retrieveUrl;

  private final S3Presigner s3Presigner;

  public S3PresignedUrlService(S3Presigner s3Presigner) {
    this.s3Presigner = s3Presigner;
  }

  public PresignedUrlResponse generatePresignedUpUrl(ImageContentType contentType) {
    var objectKey = generateObjectKey(contentType);
    var objectRequest = PutObjectRequest.builder()
      .bucket(bucketName)
      .key(objectKey)
      .contentType(contentType.getMimeType())
      .build();

    var presignRequest = PutObjectPresignRequest.builder()
      .signatureDuration(Duration.ofMinutes(10))
      .putObjectRequest(objectRequest)
      .build();

    final var presignedRequest = s3Presigner.presignPutObject(presignRequest);

    return new PresignedUrlResponse(
      presignedRequest.url().toString(),
      objectKey,
      retrieveUrl + objectKey
    );
  }

  private String generateObjectKey(ImageContentType imageContentType) {
    String fileExtension = imageContentType.name().toLowerCase();
    return "original/" + UUID.randomUUID() + "." + fileExtension;
  }
}