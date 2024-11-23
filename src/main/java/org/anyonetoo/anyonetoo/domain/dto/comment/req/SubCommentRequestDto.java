package org.anyonetoo.anyonetoo.domain.dto.comment.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "대댓글 등록 Request DTO")
@Getter
@Builder
public class SubCommentRequestDto {

    @Schema(description = "본댓글 아이디", example = "1")
    @NotNull(message = "본댓글의 아이디가 입력되지 않았습니다.")
    private Long mainCommentId;

    @Schema(description = "댓글 내용", example = "헐! 사고싶어요!")
    @NotBlank(message = "대댓글의 내용이 입력되지 않았습니다.")
    private String content;

    @Schema(description = "비밀글 여부", example = "true")
    private boolean isSecret;
}