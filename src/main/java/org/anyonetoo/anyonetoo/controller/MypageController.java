package org.anyonetoo.anyonetoo.controller;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.mypage.res.ProductResponseDTO;
import org.anyonetoo.anyonetoo.domain.dto.mypage.res.PurchaseResponseDTO;
import org.anyonetoo.anyonetoo.domain.dto.mypage.res.StatusResponseDTO;
import org.anyonetoo.anyonetoo.domain.dto.product.res.ProductSummaryResponseDto;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.enums.Status;
import org.anyonetoo.anyonetoo.service.MypageService;
import org.anyonetoo.anyonetoo.service.ProductService;
import org.anyonetoo.anyonetoo.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final ProductService productService;
    private final PurchaseService purchaseService;

    @GetMapping("/who")
    public String checkWho(@AuthenticationPrincipal User user) {
        if (user.getSeller() != null) {
            return "seller";
        } else return "consumer";
    }

    @GetMapping("/info")
    public String showMyInfo(@AuthenticationPrincipal User user){
        if (user.getSeller() != null) {
            return user.getSeller().getName();
        } else return user.getConsumer().getName();
    }

    @PatchMapping("/{purchaseId}/{status}")
    public ResponseEntity<String> updateStatus(@PathVariable Long purchaseId, Principal principal, @PathVariable Status status) {
        String userId = principal.getName();

        mypageService.updateStatus(purchaseId, status, userId);
        return ResponseEntity.ok("상태 변경 완료");
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<StatusResponseDTO> showStatus(@PathVariable Long purchaseId) {
        StatusResponseDTO status = mypageService.showStatus(purchaseId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/allproduct")
    public ResponseEntity<List<ProductSummaryResponseDto>> getSellerProducts(
            @AuthenticationPrincipal User user) {
        List<ProductSummaryResponseDto> products = productService.getSellerProducts(user.getSeller().getId());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/allpurchase/seller/{productId}")
    public ResponseEntity<List<PurchaseResponseDTO>> showAllPurchase(@PathVariable Long productId){

        List<PurchaseResponseDTO> allPurchase = purchaseService.showAllPurchases(productId);
        return ResponseEntity.ok(allPurchase);
    }
    @GetMapping("/allpurchase/consumer")
    public ResponseEntity<List<PurchaseResponseDTO>> showAllConsumerPurchase(@AuthenticationPrincipal User user){
        Long consumerId = user.getConsumer().getId();

        List<PurchaseResponseDTO> allPurchase = purchaseService.showAllConsumerPurchases(consumerId);
        return ResponseEntity.ok(allPurchase);
    }
}

