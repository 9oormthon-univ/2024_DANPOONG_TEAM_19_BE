package org.anyonetoo.anyonetoo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.video.KakaoVideoSearchResponseDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/search")
    public ResponseEntity<KakaoVideoSearchResponseDTO> search(HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        String userId = user.getId();

        KakaoVideoSearchResponseDTO response = videoService.search(userId);
        return ResponseEntity.ok(response);
    }
}
