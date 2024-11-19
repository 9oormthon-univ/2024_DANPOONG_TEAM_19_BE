package org.anyonetoo.anyonetoo.domain.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "댓글 수정 Request DTO")
@Getter
@Builder
public class UpdateCommentRequestDto {

    @Schema(description = "댓글 아이디", example = "1")
    private Long commentId;

    @Schema(description = "수정 내용", example = "강추! 천연 수세미")
    private String content;

    @Schema(description = "비밀댓글 여부", example = "true")
    private boolean secret;
}
