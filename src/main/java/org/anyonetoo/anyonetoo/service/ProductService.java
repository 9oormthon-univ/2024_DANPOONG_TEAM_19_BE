package org.anyonetoo.anyonetoo.service;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.res.*;
import org.anyonetoo.anyonetoo.domain.entity.Product;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.CommentRepository;
import org.anyonetoo.anyonetoo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CommentService commentService;


    @Transactional
    public List<ProductSummaryDto> getAllProduct(){
        return productRepository.findAll().stream()
                .map(ProductSummaryDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDto getProduct(Long productId){

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

        ProductDetailDto productDetail = ProductDetailDto.from(product);

        return ProductResponseDto.builder()
                .productDetail(productDetail)
                .mainComments(commentService.getMainComments(productId))
                .build();
    }

    @Transactional
    public List<SubCommentDto> getSubComments(Long productId, Long mainCommentId){
        return commentService.getSubComments(productId, mainCommentId);
    }

    @Transactional
    public Long deleteProduct(Long productId){
        productRepository.deleteById(productId);
        return productId;
    }

    @Transactional
    public List<ProductSummaryDto> searchProduct(String keyword){
        return productRepository.findByTitleContaining(keyword).stream()
                .map(ProductSummaryDto::from)
                .collect(Collectors.toList());
    }
}
