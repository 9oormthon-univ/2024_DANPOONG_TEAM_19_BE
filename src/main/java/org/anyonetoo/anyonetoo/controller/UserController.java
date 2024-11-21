package org.anyonetoo.anyonetoo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.user.PreferenceRequestDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/prefer")
    public ResponseEntity<String> selectCategory(@AuthenticationPrincipal User user, @Valid @RequestBody PreferenceRequestDTO selection) {

        Long sellerId = user.getSeller() != null ? user.getSeller().getId() : null;
        Long consumerId = user.getConsumer() != null ? user.getConsumer().getId() : null;

        if (sellerId == null && consumerId == null) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자 없음");}
        try {
            if (sellerId != null) {
                userService.addSellerCategories(sellerId, selection.getCategoryIds());
            } else {
                userService.addConsumerCategories(consumerId, selection.getCategoryIds());
            }
            return ResponseEntity.ok("선호 카테고리 추가 완료");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
