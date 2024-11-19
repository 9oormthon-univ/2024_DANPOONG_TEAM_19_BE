package org.anyonetoo.anyonetoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.res.*;
import org.anyonetoo.anyonetoo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/product")
public class ProductController {
    // TODO (1) - 상품 - 전체 상품 조회 ✅
    // TODO (2) - 상품 - 특정 상품 조회 ✅
    // TODO (3) - 상품 - 상품 업로드
    // TODO (4) - 상품 - 상품 삭제 ✅
    // TODO (5) - 상품 - 상품 검색 ✅
    // TODO (6) - 상품 - 대댓글 조회 ✅

    private final ProductService productService;

    @Operation(summary = "전체 상품 조회")
    @ApiResponse(responseCode = "200", description = "전체 상품 조회 성공")
    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductSummaryDto>>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getAllProduct(), "전체 상품 조회 성공"));
    }

    @Operation(summary = "특정 상품 조회")
    @ApiResponse(responseCode = "200", description = "특정 상품 조회 성공")
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<ProductResponseDto>> getProduct(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getProduct(productId), "특정 상품 조회 성공"));
    }

    @Operation(summary = "대댓글 조회")
    @ApiResponse(responseCode = "200", description = "대댓글 조회 성공")
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<List<SubCommentDto>>> getSubComment(@PathVariable Long productId,
                                                                          @RequestParam Long mainCommentId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getSubComments(productId, mainCommentId), "상품 대댓글 조회 성공"));
    }

    @Operation(summary = "상품 삭제")
    @ApiResponse(responseCode = "200", description = "상품 삭제 완료")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto<Long>> deleteProduct(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.deleteProduct(productId), "상품 삭제 성공"));
    }

    @Operation(summary = "상품 검색")
    @ApiResponse(responseCode = "200", description = "상품 검색 완료")
    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductSummaryDto>>> searchProduct(@RequestParam String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.searchProduct(keyword), "키워드로 상품 조회 성공"));
    }
}
