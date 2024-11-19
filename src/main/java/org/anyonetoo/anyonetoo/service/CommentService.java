package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.res.MainCommentDto;
import org.anyonetoo.anyonetoo.domain.dto.res.SubCommentDto;
import org.anyonetoo.anyonetoo.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<MainCommentDto> getMainComments(Long productId){
        return commentRepository.findAllMainComments(productId).stream()
                .map(MainCommentDto::from)
                .collect(Collectors.toList());
    }

    public List<SubCommentDto> getSubComments(Long productId, Long mainProductId){
        return commentRepository.findAllSubComments(productId, mainProductId).stream()
                .map(SubCommentDto::from)
                .collect(Collectors.toList());
    }
}
