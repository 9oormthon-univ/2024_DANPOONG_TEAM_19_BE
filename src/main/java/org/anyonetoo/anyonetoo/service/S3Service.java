//package org.anyonetoo.anyonetoo.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//import software.amazon.awssdk.services.s3.presigner.S3Presigner;
//import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
//import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
//
//import java.time.Duration;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class S3Service {
//
//    private final AmazonS3 amazonS3;
//
//    @Value("${spring.cloud.aws.s3.bucket}")
//    private String bucket;
//
//
//    public String generatePresignedUrl(String fileName) {
//        String path = createPath(fileName);
//
//        S3Presigner presigner = S3Presigner.builder()
//                .region(s3Client.serviceClientConfiguration().region())
//                .credentialsProvider(s3Client.serviceClientConfiguration().credentialsProvider())
//                .build();
//
//        PutObjectRequest objectRequest = PutObjectRequest.builder()
//                .bucket(bucket)
//                .key(path)
//                .acl("public-read")  // CannedACL 설정
//                .build();
//
//        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
//                .signatureDuration(Duration.ofMinutes(5))  // 5분 만료
//                .putObjectRequest(objectRequest)
//                .build();
//
//        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
//
//        presigner.close();
//
//        return presignedRequest.url().toString();
//    }
//
//    private String createFileId() {
//        return UUID.randomUUID().toString();
//    }
//
//    private String createPath(String fileName) {
//        String fileId = createFileId();
//        return String.format("image/%s", fileId + fileName);
//    }
//}