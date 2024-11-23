package org.anyonetoo.anyonetoo.domain.dto.product.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.dto.comment.res.MainCommentResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.product.res.ProductDetailResponseDto;

import java.util.List;

@Schema(description = "특정 상품 상세 정보 ResponseDTO")
@Getter
@Builder
public class ProductResponseDto {
    @Schema(description = "상품 상세정보")
    private ProductDetailResponseDto productDetail;

    @Schema(description = "상품 댓글")
    private List<MainCommentResponseDto> mainComments;
}
