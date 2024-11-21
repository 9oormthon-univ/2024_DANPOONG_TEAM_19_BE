package org.anyonetoo.anyonetoo.repository;

import org.anyonetoo.anyonetoo.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.product.productId = :productId AND c.mainCommentId = -1")
    List<Comment> findAllMainComments(@Param("productId") Long productId);

    @Query("SELECT c FROM Comment c WHERE c.product.productId = :productId AND c.mainCommentId = :mainCommentId")
    List<Comment> findAllSubComments(@Param("productId") Long productId, @Param("mainCommentId") Long mainCommentId);

    boolean existsByMainCommentId(Long mainCommentId);
}
