package org.anyonetoo.anyonetoo.domain.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.Product;

@Schema(description = "특정 상품 DTO - 정보")
@Getter
@Builder
public class ProductDetailDto {

    @Schema(description = "상품 PK", example = "1")
    private Long productId;

    @Schema(description = "상품 제목", example = "천연 섬유로 제작한 수세미")
    private String title;

    @Schema(description = "상품 설명", example = "손에 피로를 줄여주는 수세미입니다")
    private String content;

    @Schema(description = "상품 가격", example = "12000")
    private Long price;

    @Schema(description = "상품 이미지 url", example = "https://bucket-name.s3.region.amazonaws.com/folder/image.jpg")
    private String imgUrl;

    @Schema(description = "판매자 이름", example = "김옥자")
    private String sellerName;

    public static ProductDetailDto from(Product product){
        return ProductDetailDto.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
                .content(product.getContent())
                .price(product.getPrice())
                .imgUrl(product.getImgUrl())
                .sellerName(product.getSeller().getName())
                .build();
    }
}
