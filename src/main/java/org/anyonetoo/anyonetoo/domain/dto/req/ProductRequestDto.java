package org.anyonetoo.anyonetoo.domain.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "상품 등록 Request DTO")
@Getter
@NoArgsConstructor
public class ProductRequestDto {

    @Schema(description = "상품 제목", example = "천연 섬유로 제작한 수세미")
    @NotBlank(message = "상품 제목이 입력되지 않았습니다.")
    private String title;

    @Schema(description = "상품 설명", example = "천연 섬유로 제작한 수세미입니다! 많은 관심 부탁드려요")
    @NotBlank(message = "상품 설명이 입력되지 않았습니다.")
    private String content;

    @Schema(description = "상품 가격", example = "12000")
    @NotNull(message = "상품 가격이 입력되지 않았습니다.")
    @Min(value = 0, message = "가격은 0보다 작을 수 없습니다")
    private Long price;

    @Schema(description = "상품에 포함된 이미지 개수(s3-preSignedUrl 반환용)", example = "https://anyonetoo-bucket.s3.ap-northeast-2.amazonaws.com/example.jpg")
    private Integer imageCount;
}