package org.anyonetoo.anyonetoo.controller;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.video.res.KakaoVideoSearchResponseDTO;
import org.anyonetoo.anyonetoo.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search/keyword")
    public ResponseEntity<KakaoVideoSearchResponseDTO> searchKeyword(Principal principal, @RequestParam String query) {


        KakaoVideoSearchResponseDTO response = videoService.searchKeyword(query);
        return ResponseEntity.ok(response);
    }
}
