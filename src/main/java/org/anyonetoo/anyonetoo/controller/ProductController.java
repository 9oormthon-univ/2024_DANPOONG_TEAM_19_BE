package org.anyonetoo.anyonetoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.req.MainCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.req.ProductRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.req.SubCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.req.UpdateCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.res.*;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.service.ProductService;
import org.anyonetoo.anyonetoo.service.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/product")
public class ProductController {

    private final ProductService productService;
    private final S3Service s3Service;

    /**
     * 상품 관련
     */
    @Operation(summary = "전체 상품 조회")
    @ApiResponse(responseCode = "200", description = "전체 상품 조회 성공")
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ProductSummaryDto>>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getAllProduct(), "전체 상품 조회 성공"));
    }

    @Operation(summary = "특정 상품 조회")
    @ApiResponse(responseCode = "200", description = "특정 상품 조회 성공")
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<ProductResponseDto>> getProduct(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getProduct(productId), "특정 상품 조회 성공"));
    }

    @Operation(summary = "상품 등록")
    @ApiResponse(responseCode = "201", description = "상품 등록 성공")
    @PostMapping()
    public ResponseEntity<ResponseDto<Long>> saveProduct(@AuthenticationPrincipal User user,
                                                         @Valid @RequestBody ProductRequestDto request){
        System.out.println(user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveProduct(user.getUserId(), request), "상품 등록 성공"));
    }


    @Operation(summary = "상품 삭제")
    @ApiResponse(responseCode = "200", description = "상품 삭제 완료")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto<Long>> deleteProduct(HttpServletRequest req,
                                                            @PathVariable Long productId){
        User user = (User) req.getAttribute("user");
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.deleteProduct(user.getUserId(), productId), "상품 삭제 성공"));
    }

    @Operation(summary = "상품 검색")
    @ApiResponse(responseCode = "200", description = "상품 검색 완료")
    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductSummaryDto>>> searchProduct(@RequestParam String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.searchProduct(keyword), "키워드로 상품 조회 성공"));
    }

    /*
     * 댓글 관련
     */
    @Operation(summary = "대댓글 조회")
    @ApiResponse(responseCode = "200", description = "대댓글 조회 성공")
    @GetMapping("/{productId}/comment")
    public ResponseEntity<ResponseDto<List<SubCommentResponseDto>>> getSubComment(@PathVariable Long productId,
                                                                                  @RequestParam Long mainCommentId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getSubComments(productId, mainCommentId), "상품 대댓글 조회 성공"));
    }

    @Operation(summary = "본댓글 등록")
    @ApiResponse(responseCode = "201", description = "본댓글 등록 완료")
    @PostMapping("/{productId}/comment")
    public ResponseEntity<ResponseDto<Long>> saveMainComment(@AuthenticationPrincipal User user,
                                                             @PathVariable Long productId,
                                                             @Valid @RequestBody MainCommentRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveMainComment(user.getUserId(), productId, request), "본댓글 등록 성공"));
    }

    @Operation(summary = "대댓글 등록")
    @ApiResponse(responseCode = "201", description = "대댓글 등록 완료")
    @PostMapping("/{productId}/re-comment")
    public ResponseEntity<ResponseDto<Long>> saveSubComment(@AuthenticationPrincipal User user,
                                                            @PathVariable Long productId,
                                                            @Valid @RequestBody SubCommentRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveSubComment(user.getUserId(), productId, request), "대댓글 등록 성공"));
    }

    @Operation(summary = "댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글 수정 완료")
    @PutMapping("/comment")
    public ResponseEntity<ResponseDto<Long>> updateComment(@AuthenticationPrincipal User user,
                                                           @Valid @RequestBody UpdateCommentRequestDto request){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.updateComment(user.getUserId(), request), "댓글 수정 완료"));
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 완료")
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ResponseDto<Long>> deleteComment(@AuthenticationPrincipal User user,
                                                           @PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.deleteComment(user.getUserId(), commentId), "댓글 삭제 완료"));
    }

    /**
     * 구매 요청
     */
    @Operation(summary = "구매 요청")
    @ApiResponse(responseCode = "200", description = "구매 요청 전송 완료")
    @PostMapping("/{productId}/orders")
    public ResponseEntity<ResponseDto<Long>> createOrder(@AuthenticationPrincipal User user,
                                                         @PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.createOrder(user.getUserId(), productId), "구매 요청 전송 완료"));
    }
}