package org.anyonetoo.anyonetoo.domain.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.Product;

@Schema(description = "전체 상품 조회 Response DTO")
@Getter
@Builder
public class ProductSummaryDto {

    @Schema(description = "상품 PK", example = "1")
    private Long productId;

    @Schema(description = "상품 제목", example = "천연 섬유로 제작한 수세미")
    private String title;

    @Schema(description = "상품 가격", example = "12000")
    private Long price;

    @Schema(description = "상품 이미지 url", example = "https://bucket-name.s3.region.amazonaws.com/folder/image.jpg")
    private String imgUrl;

    @Schema(description = "판매자 이름", example = "김옥자")
    private String sellerName;

    public static ProductSummaryDto from(Product product){
        return ProductSummaryDto.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
                .price(product.getPrice())
                .imgUrl(product.getImgUrl())
                .sellerName(product.getSeller().getName())
                .build();
    }
}
