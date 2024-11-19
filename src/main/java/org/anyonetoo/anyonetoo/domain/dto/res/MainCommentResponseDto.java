package org.anyonetoo.anyonetoo.domain.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.Comment;

import java.time.LocalDateTime;

@Schema(description = "본댓글 조회 Response DTO")
@Getter
@Builder
public class MainCommentResponseDto {

    @Schema(description = "댓글 아이디", example = "1")
    private Long commentId;

    @Schema(description = "상품 아이디", example = "1")
    private Long productId;

    @Schema(description = "작성자", example = "옥자")
    private String username;

    @Schema(description = "댓글 내용", example = "헐! 사고싶어요!")
    private String content;

    // Todo BaseEntity 관련 수정 필요(createdAt기준 정렬가능하도록)
    @Schema(description = "작성일", example = "2024-10-22")
    private LocalDateTime createdAt;

    public static MainCommentResponseDto from(Comment comment){
        return MainCommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .productId(comment.getProduct().getProductId())
                .username(comment.getProduct().getSeller().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}