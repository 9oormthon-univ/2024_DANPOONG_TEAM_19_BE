package org.anyonetoo.anyonetoo.domain.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.anyonetoo.anyonetoo.domain.dto.image.PreSignedUrlResponseDto;

import java.util.List;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "상품 저장 응답 DTO")
public class ProductSaveResponseDto {

    @Schema(description = "저장된 상품 ID")
    private Long productId;

    @Schema(description = "이미지 업로드용 Presigned URL 목록")
    private List<PreSignedUrlResponseDto> presignedUrls;

    public static ProductSaveResponseDto of(Long productId, List<PreSignedUrlResponseDto> presignedUrls) {
        return ProductSaveResponseDto.builder()
                .productId(productId)
                .presignedUrls(presignedUrls)
                .build();
    }
}