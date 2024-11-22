//package org.anyonetoo.anyonetoo.controller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.anyonetoo.anyonetoo.domain.dto.image.PreSignedUrlResponseDto;
//import org.anyonetoo.anyonetoo.domain.entity.Product;
//import org.anyonetoo.anyonetoo.exception.RestApiException;
//import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
//import org.anyonetoo.anyonetoo.repository.ProductRepository;
//import org.anyonetoo.anyonetoo.service.S3Service;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/test/s3")
//public class S3Controller {
//
//    private final S3Service s3Service;
//    private final ProductRepository productRepository;
//
//    @GetMapping("/upload-url")
//    public PreSignedUrlResponseDto getUploadUrl() {
//        Product product = productRepository.findById(1L)
//                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));
//
//        return s3Service.generateUploadPreSignedUrl("test-product", product);
//    }
//}
