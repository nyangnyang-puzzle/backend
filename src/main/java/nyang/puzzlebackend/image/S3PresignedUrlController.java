package nyang.puzzlebackend.image;

import nyang.puzzlebackend.api.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class S3PresignedUrlController {

    private final S3PresignedUrlService s3PresignedUrlService;

    public S3PresignedUrlController(S3PresignedUrlService s3PresignedUrlService) {
        this.s3PresignedUrlService = s3PresignedUrlService;
    }

    @GetMapping("/api/presigned/upload")
    public ApiResponse<PresignedUrlResponse> generateUploadPresignedUrl(
      @RequestParam(required = false) String fileExtension
    ) {
        var imageContentType = ImageContentType.from(fileExtension);
        var presignedUrlResponse = s3PresignedUrlService.generatePresignedUpUrl(imageContentType);
        return ApiResponse.ok(presignedUrlResponse);
    }
}