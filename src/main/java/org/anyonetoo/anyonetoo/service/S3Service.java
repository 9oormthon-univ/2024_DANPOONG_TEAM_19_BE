package org.anyonetoo.anyonetoo.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.image.PreSignedUrlResponseDto;
import org.anyonetoo.anyonetoo.domain.entity.Image;
import org.anyonetoo.anyonetoo.domain.entity.Product;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class S3Service {
    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public PreSignedUrlResponseDto generateUploadPreSignedUrl(String productTitle, Product product) {
        String objectKey = createObjectKey(productTitle);

        Image image = Image.builder()
                .imageUrl(objectKey)
                .product(product)
                .build();

        imageRepository.save(image);

        String preSignedUrl = amazonS3.generatePresignedUrl(bucketName,
                objectKey,
                getExpiredDate(),
                HttpMethod.PUT).toString();

        return PreSignedUrlResponseDto.of(image.getImageId(), preSignedUrl);
    }

    public String getImageUrl(String objectKey) {
        return amazonS3.generatePresignedUrl(bucketName,
                objectKey,
                getExpiredDate(),
                HttpMethod.GET).toString();
    }

    private String createObjectKey(String fileName) {
        return "products/" + fileName + "-" + UUID.randomUUID();
    }

    private Date getExpiredDate() {
        return Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
                .plusHours(1)
                .toInstant());
    }
}