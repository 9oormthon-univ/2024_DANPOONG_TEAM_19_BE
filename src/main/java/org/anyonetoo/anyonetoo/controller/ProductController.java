package org.anyonetoo.anyonetoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/product")
public class ProductController {
    // TODO (1) - 상품 - 전체 상품 조회 ✅
    // TODO (2) - 상품 - 특정 상품 조회 ✅
    // TODO (3) - 상품 - 상품 업로드 ✅
    // TODO (4) - 상품 - 상품 삭제 ✅
    // TODO (5) - 상품 - 상품 검색 ✅
    // TODO (6) - 상품 - 대댓글 조회 ✅
    // TODO (7) - 상품 - 본댓글 작성 ✅
    // TODO (8) - 상품 - 대댓글 작성 ✅
    // TODO (9) - 상품 - 댓글 수정 ✅
    // TODO (10) - 상품 - 댓글 삭제 ✅
    // TODO (11) - 상품 - 구매 요청 ✅

    private final ProductService productService;
    private final S3Service s3Service;

    // 상품 관련
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

    @Operation(summary = "상품 등록")
    @ApiResponse(responseCode = "201", description = "상품 등록 성공")
    @PostMapping()
    public ResponseEntity<ResponseDto<Long>> saveProduct(HttpServletRequest req,
                                                         @RequestBody ProductRequestDto request){
        User user = (User) req.getAttribute("user");
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveProduct(user.getUserId(), request), "상품 등록 성공"));
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

    // 댓글 관련
    @Operation(summary = "대댓글 조회")
    @ApiResponse(responseCode = "200", description = "대댓글 조회 성공")
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<List<SubCommentResponseDto>>> getSubComment(@PathVariable Long productId,
                                                                                  @RequestParam Long mainCommentId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getSubComments(productId, mainCommentId), "상품 대댓글 조회 성공"));
    }

    @Operation(summary = "본댓글 등록")
    @ApiResponse(responseCode = "201", description = "본댓글 등록 완료")
    @PostMapping("/comment")
    public ResponseEntity<ResponseDto<Long>> saveMainComment(HttpServletRequest req,
                                                         @RequestBody MainCommentRequestDto request){
        User user = (User) req.getAttribute("user");
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveMainComment(user.getUserId(), request), "본댓글 등록 성공"));
    }

    @Operation(summary = "대댓글 등록")
    @ApiResponse(responseCode = "201", description = "대댓글 등록 완료")
    @PostMapping("/re-comment")
    public ResponseEntity<ResponseDto<Long>> saveSubComment(HttpServletRequest req,
                                                            @RequestBody SubCommentRequestDto request){
        User user = (User) req.getAttribute("user");
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveSubComment(user.getUserId(), request), "대댓글 등록 성공"));
    }

    @Operation(summary = "댓글 수정")
    @ApiResponse(responseCode = "200", description = "댓글 수정 완료")
    @PutMapping("/comment")
    public ResponseEntity<ResponseDto<Long>> updateComment(HttpServletRequest req,
                                                           @RequestBody UpdateCommentRequestDto request){
        User user = (User) req.getAttribute("name");
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.updateComment(user.getUserId(), request), "댓글 수정 완료"));
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 완료")
    @DeleteMapping("/coment/{commentId}")
    public ResponseEntity<ResponseDto<Long>> deleteComment(HttpServletRequest req,
                                                           @PathVariable Long commentId){
        User user = (User) req.getAttribute("name");
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.deleteComment(user.getUserId(), commentId), "댓글 삭제 완료"));
    }

    @Operation(summary = "구매 요청")
    @ApiResponse(responseCode = "200", description = "구매 요청 전송 완료")
    @PostMapping("/{productId}/orders")
    public ResponseEntity<ResponseDto<Long>> createOrder(HttpServletRequest req,
                                                         @PathVariable Long productId){
        User user = (User) req.getAttribute("name");
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.createOrder(user.getUserId(), productId), "구매 요청 전송 완료"));
    }
}