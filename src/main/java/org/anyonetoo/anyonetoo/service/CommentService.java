package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.req.MainCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.req.SubCommentRequestDto;
import org.anyonetoo.anyonetoo.domain.dto.res.MainCommentResponseDto;
import org.anyonetoo.anyonetoo.domain.dto.res.SubCommentResponseDto;
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
import java.util.stream.Collectors;

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

    public List<SubCommentResponseDto> getSubComments(Long productId, Long mainProductId){
        return commentRepository.findAllSubComments(productId, mainProductId).stream()
                .map(SubCommentResponseDto::from)
                .collect(Collectors.toList());
    }

    public Long saveMainComment(Long userId, MainCommentRequestDto request){

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

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

    public Long saveSubComment(Long userId, SubCommentRequestDto request){

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PRODUCT_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.USER_NOT_FOUND));

        if(!commentRepository.existsByMainCommentId(request.getMainCommentId()))
            throw new RestApiException(CustomErrorCode.MAIN_COMMENT_NOT_FOUND);

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
}
