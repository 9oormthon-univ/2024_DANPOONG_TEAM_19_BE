package org.anyonetoo.anyonetoo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.comment.res.SubCommentResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.product.res.ProductResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.product.res.ProductSaveResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.comment.req.MainCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.product.req.ProductRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.comment.req.SubCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.comment.req.UpdateCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.product.res.ProductSummaryResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.res.*;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.service.ProductService;
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

    /**
     * 상품 관련
     */
    @Operation(summary = "전체 상품 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "전체 상품 조회 성공")
    })
    @GetMapping("/all")
    public ResponseEntity<ResponseDto<List<ProductSummaryResponseDto>>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getAllProduct(), "전체 상품 조회 성공"));
    }

    @Operation(summary = "특정 상품 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품 조회 실패 : 상품 없음")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<ProductResponseDto>> getProduct(@PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getProduct(productId), "특정 상품 조회 성공"));
    }
    @PostMapping
    public ResponseEntity<ResponseDto<ProductSaveResponseDto>> saveProduct(@AuthenticationPrincipal User user,
                                                                           @Valid @RequestBody ProductRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveProduct(user.getUserId(), request), "상품 등록 성공"));
    }


    @Operation(summary = "상품 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상품 조회 실패"),
            @ApiResponse(responseCode = "404", description = "유저 조회 실패"),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto<Long>> deleteProduct(@AuthenticationPrincipal User user,
                                                            @PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.deleteProduct(user.getUserId(), productId), "상품 삭제 성공"));
    }

    @Operation(summary = "상품 검색")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "상품 검색 성공")
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductSummaryResponseDto>>> searchProduct(@RequestParam String keyword){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.searchProduct(keyword), "키워드로 상품 조회 성공"));
    }

    /*
     * 댓글 관련
     */
    @Operation(summary = "대댓글 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "대댓글 조회 성공"),
        @ApiResponse(responseCode = "404", description = "대댓글 조회 실패 : 본댓글 조회 실패"),
        @ApiResponse(responseCode = "404", description = "대댓글 조회 실패 : 상품 조회 실패")
    })
    @GetMapping("/{productId}/comment")
    public ResponseEntity<ResponseDto<List<SubCommentResponseDto>>> getSubComment(@PathVariable Long productId,
                                                                                  @RequestParam Long mainCommentId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.getSubComments(productId, mainCommentId), "상품 대댓글 조회 성공"));
    }

    @Operation(summary = "본댓글 등록")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "본댓글 등록 성공"),
        @ApiResponse(responseCode = "404", description = "본댓글 등록 실패 : 상품 조회 실패"),
        @ApiResponse(responseCode = "404", description = "본댓글 등록 실패 : 유저 조회 실패")
    })
    @PostMapping("/{productId}/comment")
    public ResponseEntity<ResponseDto<Long>> saveMainComment(@AuthenticationPrincipal User user,
                                                             @PathVariable Long productId,
                                                             @Valid @RequestBody MainCommentRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveMainComment(user.getUserId(), productId, request), "본댓글 등록 성공"));
    }

    @Operation(summary = "대댓글 등록")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "대댓글 등록 성공"),
        @ApiResponse(responseCode = "404", description = "대댓글 등록 실패 : 상품 조회 실패"),
        @ApiResponse(responseCode = "404", description = "대댓글 등록 실패 : 유저 조회 실패"),
        @ApiResponse(responseCode = "404", description = "대댓글 등록 실패 : 본댓글 조회 실패")
    })
    @PostMapping("/{productId}/re-comment")
    public ResponseEntity<ResponseDto<Long>> saveSubComment(@AuthenticationPrincipal User user,
                                                            @PathVariable Long productId,
                                                            @Valid @RequestBody SubCommentRequestDto request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.of(productService.saveSubComment(user.getUserId(), productId, request), "대댓글 등록 성공"));
    }

    @Operation(summary = "댓글 수정")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
        @ApiResponse(responseCode = "404", description = "댓글 수정 실패 : 본댓글 조회 실패"),
        @ApiResponse(responseCode = "403", description = "댓글 수정 실패 : 수정 권한 없음")
    })
    @PutMapping("/comment")
    public ResponseEntity<ResponseDto<Long>> updateComment(@AuthenticationPrincipal User user,
                                                           @Valid @RequestBody UpdateCommentRequestDto request){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.updateComment(user.getUserId(), request), "댓글 수정 완료"));
    }

    @Operation(summary = "댓글 삭제")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "댓글 삭제 성공"),
        @ApiResponse(responseCode = "404", description = "댓글 삭제 실패 : 댓글 조회 실패"),
        @ApiResponse(responseCode = "403", description = "댓글 삭제 실패 : 삭제 권한 없음")
    })
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ResponseDto<Long>> deleteComment(@AuthenticationPrincipal User user,
                                                           @PathVariable Long commentId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.deleteComment(user.getUserId(), commentId), "댓글 삭제 완료"));
    }

    /**
     * 구매 요청
     */
    @Operation(summary = "구매 요청")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "구매 요청 성공"),
        @ApiResponse(responseCode = "404", description = "구매 요청 실패 : 상품 조회 실패"),
        @ApiResponse(responseCode = "404", description = "구매 요청 실패 : 유저 조회 실패")
    })
    @PostMapping("/{productId}/orders")
    public ResponseEntity<ResponseDto<Long>> createOrder(@AuthenticationPrincipal User user,
                                                         @PathVariable Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.of(productService.createOrder(user.getUserId(), productId), "구매 요청 전송 완료"));
    }
}