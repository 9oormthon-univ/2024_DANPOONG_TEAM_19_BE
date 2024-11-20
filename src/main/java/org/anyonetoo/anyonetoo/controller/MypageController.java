package org.anyonetoo.anyonetoo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.mypage.ProductResponseDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.enums.Status;
import org.anyonetoo.anyonetoo.service.MypageService;
import org.anyonetoo.anyonetoo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final ProductService productService;

    @PatchMapping("/{purchaseId}/{status}")
    public ResponseEntity<String> updateStatus(@PathVariable Long purchaseId,HttpServletRequest req, @PathVariable Status status) {
        User user = (User) req.getAttribute("user");
        if(user.getSeller()!=null) {
            mypageService.updateStatus(purchaseId, status);
            return ResponseEntity.ok("상태 변경 완료");
        }else{
            return ResponseEntity.status(400).body("상태는 판매자만 변경 가능합니다");
        }
    }

    @GetMapping("/{purchaseId}")
    public ResponseEntity<String> showStatus(@PathVariable Long purchaseId) {
        String status = mypageService.showStatus(purchaseId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/allproduct")
    public ResponseEntity<List<ProductResponseDTO>> showAllProduct(HttpServletRequest req){
        User user = (User) req.getAttribute("user");
        Long sellerId = user.getSeller().getId();

        List<ProductResponseDTO> allProduct = productService.showAllProducts(sellerId);
        return ResponseEntity.ok(allProduct);
    }
}
