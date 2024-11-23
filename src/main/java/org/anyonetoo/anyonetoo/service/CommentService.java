package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.anyonetoo.anyonetoo.domain.dto.comment.req.MainCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.comment.req.SubCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.comment.req.UpdateCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.comment.res.MainCommentResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.comment.res.SubCommentResponseDto;
import org.anyonetoo.anyonetoo.domain.entity.Comment;
import org.anyonetoo.anyonetoo.domain.entity.Product;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.CommentRepository;
import org.anyonetoo.anyonetoo.repository.ProductRepository;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<MainCommentResponseDto> getMainComments(Long productId){
        return commentRepository.findAllMainComments(productId).stream()
                .map(MainCommentResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<SubCommentResponseDto> getSubComments(Long productId, Long mainCommentId){

        if(!commentRepository.existsByMainCommentId(mainCommentId))
            throw new RestApiException(CustomErrorCode.COMMENT_NOT_FOUND);

        return commentRepository.findAllSubComments(productId, mainCommentId).stream()
                .map(SubCommentResponseDto::from)
                .collect(Collectors.toList());
    }

    public Long saveMainComment(Long userId, Product product, MainCommentRequestDto request){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .isSecret(request.isSecret())
                .mainCommentId(-1L)
                .product(product)
                .user(user)
                .build();

        Comment savedComment =  commentRepository.save(comment);
        return savedComment.getCommentId();
    }

    public Long saveSubComment(Long userId, Product product, SubCommentRequestDto request){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.USER_NOT_FOUND));

        if(!commentRepository.existsByMainCommentId(request.getMainCommentId()))
            throw new RestApiException(CustomErrorCode.COMMENT_NOT_FOUND);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .isSecret(request.isSecret())
                .mainCommentId(request.getMainCommentId())
                .product(product)
                .user(user)
                .build();

        Comment savedComment =  commentRepository.save(comment);
        return savedComment.getCommentId();
    }

    public Long updateComment(Long userId, UpdateCommentRequestDto request){

        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.COMMENT_NOT_FOUND));

        if(!Objects.equals(userId, comment.getUser().getUserId()))
            throw new RestApiException(CustomErrorCode.NO_PERMISSION_FOR_COMMENT);

        comment.updateComment(request.getContent(), request.isSecret());

        return comment.getCommentId();
    }

    public Long deleteComment(Long userId, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.COMMENT_NOT_FOUND));

        if(!Objects.equals(userId, comment.getUser().getUserId()))
            throw new RestApiException(CustomErrorCode.NO_PERMISSION_FOR_COMMENT);

        commentRepository.deleteById(commentId);

        return comment.getCommentId();
    }
}