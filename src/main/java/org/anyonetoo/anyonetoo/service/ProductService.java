package org.anyonetoo.anyonetoo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.image.PreSignedUrlResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.mypage.ProductResponseDTO;
import org.anyonetoo.anyonetoo.domain.dto.req.MainCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.product.ProductSaveResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.req.ProductRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.req.SubCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.req.UpdateCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.res.*;
import org.anyonetoo.anyonetoo.domain.entity.*;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.ConsumerRepository;
import org.anyonetoo.anyonetoo.repository.ProductRepository;
import org.anyonetoo.anyonetoo.repository.SellerRepository;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final S3Service s3Service;

    private final CommentService commentService;
    private final PurchaseService purchaseService;
    @Transactional
    public List<ProductSummaryDto> getAllProduct() {
        return productRepository.findAll().stream()
                .map(product -> {
                    String thumbnailUrl = s3Service.getImageUrl(product.getImages().get(0).getImageUrl());

                    return ProductSummaryDto.builder()
                            .productId(product.getProductId())
                            .title(product.getTitle())
                            .price(product.getPrice())
                            .imgUrl(thumbnailUrl)
                            .sellerName(product.getSeller().getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

        List<String> imageUrls = product.getImages().stream()
                .map(image -> s3Service.getImageUrl(image.getImageUrl()))
                .collect(Collectors.toList());

        ProductDetailDto productDetail = ProductDetailDto.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .imgUrl(imageUrls)
                .sellerName(product.getSeller().getName())
                .build();

        return ProductResponseDto.builder()
                .productDetail(productDetail)
                .mainComments(commentService.getMainComments(productId))
                .build();
    }
    @Transactional
    public List<SubCommentResponseDto> getSubComments(Long productId, Long mainCommentId){
        if(!productRepository.existsById(productId))
            throw new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND);

        return commentService.getSubComments(productId, mainCommentId);
    }

    @Transactional
    public Long deleteProduct(Long userId, Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

        Seller seller = sellerRepository.findByUserId(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.USER_NOT_FOUND));

        if(!Objects.equals(product.getSeller().getUser().getUserId(), userId))
            throw new RestApiException(CustomErrorCode.NO_PERMISSION_FOR_DELETE_PRODUCT);

        productRepository.deleteById(productId);
        return productId;
    }

    @Transactional
    public List<ProductSummaryDto> searchProduct(String keyword){
        return productRepository.findByTitleContaining(keyword).stream()
                .map(product -> {
                    String thumbnailUrl = s3Service.getImageUrl(product.getImages().get(0).getImageUrl());

                    return ProductSummaryDto.builder()
                            .productId(product.getProductId())
                            .title(product.getTitle())
                            .price(product.getPrice())
                            .imgUrl(thumbnailUrl)
                            .sellerName(product.getSeller().getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductSaveResponseDto saveProduct(Long userId, ProductRequestDto request) {
        Seller seller = sellerRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.SELLER_NOT_FOUND));

        Product product = Product.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .price(request.getPrice())
                .seller(seller)
                .build();

        productRepository.save(product);

        List<PreSignedUrlResponseDto> preSignedUrls = IntStream.range(0, request.getImageCount())
                .mapToObj(i -> s3Service.generateUploadPreSignedUrl(request.getTitle(), product))
                .collect(Collectors.toList());

        return ProductSaveResponseDto.builder()
                .productId(product.getProductId())
                .presignedUrls(preSignedUrls)
                .build();
    }

    @Transactional
    public Long saveMainComment(Long userId, Long productId, MainCommentRequestDto request){

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

        return commentService.saveMainComment(userId, product, request);
    }

    @Transactional
    public Long saveSubComment(Long userId, Long productId, SubCommentRequestDto request){

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

        return commentService.saveSubComment(userId, product, request);
    }

    @Transactional
    public Long updateComment(Long userId, UpdateCommentRequestDto request){
        return commentService.updateComment(userId, request);
    }

    @Transactional
    public Long deleteComment(Long userId, Long commentId){
        return commentService.deleteComment(userId, commentId);
    }

    @Transactional
    public Long createOrder(Long userId, Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

        return purchaseService.savePurchase(userId, product);
    }

    public List<ProductResponseDTO> showAllProducts(Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);

        // 상품이 없으면 예외 처리
//        if (products.isEmpty()) {
//            throw new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND);
//        }

        return products.stream()
                .map(ProductResponseDTO::from)
                .collect(Collectors.toList());
    }
}