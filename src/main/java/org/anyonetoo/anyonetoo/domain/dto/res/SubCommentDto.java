package org.anyonetoo.anyonetoo.domain.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.Comment;

import java.time.LocalDateTime;

@Schema(description = "대댓글 조회 Response DTO")
@Getter
@Builder
public class SubCommentDto {
    @Schema(description = "댓글 아이디", example = "1")
    private Long commentId;

    @Schema(description = "본댓글 아이디", example = "1")
    private Long mainCommentId;

    @Schema(description = "작성자", example = "옥자")
    private String username;

    @Schema(description = "댓글 내용", example = "헐! 사고싶어요!")
    private String content;

    @Schema(description = "작성일", example = "2024-10-22")
    private LocalDateTime createdAt;

    public static SubCommentDto from(Comment comment){
        return SubCommentDto.builder()
                .commentId(comment.getCommentId())
                .mainCommentId(comment.getMainCommentId())
                .username(comment.getProduct().getSeller().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
