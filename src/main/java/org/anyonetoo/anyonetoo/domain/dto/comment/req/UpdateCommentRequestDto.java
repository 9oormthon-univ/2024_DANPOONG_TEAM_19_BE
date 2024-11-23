package org.anyonetoo.anyonetoo.domain.dto.comment.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "댓글 수정 Request DTO")
@Getter
@Builder
public class UpdateCommentRequestDto {

    @Schema(description = "댓글 아이디", example = "1")
    @NotNull(message = "수정할 댓글의 아이디가 입력되지 않았습니다.")
    private Long commentId;

    @Schema(description = "수정 내용", example = "강추! 천연 수세미")
    @NotBlank(message = "수정할 댓글의 내용이 입력되지 않았습니다.")
    private String content;

    @Schema(description = "비밀댓글 여부", example = "true")
    private boolean secret;
}
