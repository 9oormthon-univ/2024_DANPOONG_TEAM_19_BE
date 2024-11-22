package org.anyonetoo.anyonetoo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.video.KakaoVideoSearchResponseDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/core/kakao")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/search")
    public ResponseEntity<KakaoVideoSearchResponseDTO> search(Principal principal) {
        String userId = principal.getName();

        KakaoVideoSearchResponseDTO response = videoService.search(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<KakaoVideoSearchResponseDTO> searchKeyword(Principal principal, @PathVariable String query) {
        String userId = principal.getName();

        KakaoVideoSearchResponseDTO response = videoService.searchKeyword(query);
        return ResponseEntity.ok(response);
    }
}
