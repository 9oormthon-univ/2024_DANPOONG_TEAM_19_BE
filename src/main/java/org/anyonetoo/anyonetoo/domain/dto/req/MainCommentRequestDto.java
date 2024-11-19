package org.anyonetoo.anyonetoo.domain.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.Comment;

import java.time.LocalDateTime;

@Schema(description = "본댓글 등록 Request DTO")
@Getter
@Builder
public class MainCommentRequestDto {

    @Schema(description = "상품 아이디", example = "1")
    private Long productId;

    @Schema(description = "댓글 내용", example = "헐! 사고싶어요!")
    private String content;

    @Schema(description = "비밀글 여부", example = "true")
    private boolean isSecret;
}