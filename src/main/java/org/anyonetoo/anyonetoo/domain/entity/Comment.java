package org.anyonetoo.anyonetoo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.anyonetoo.anyonetoo.domain.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String content;

    private Long mainCommentId;

    private boolean isSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(String content, boolean isSecret, Long mainCommentId, Product product, User user){
        this.content = content;
        this.isSecret = isSecret;
        this.mainCommentId = mainCommentId;
        this.product = product;
        this.user = user;
    }

    public void updateComment(String content, boolean isSecret){
        this.content = content;
        this.isSecret = isSecret;
    }
}