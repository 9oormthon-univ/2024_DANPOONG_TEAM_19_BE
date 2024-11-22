package org.anyonetoo.anyonetoo.service;


import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.enums.Status;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.AlarmRepository;
import org.anyonetoo.anyonetoo.repository.PurchaseRepository;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    public void updateStatus(Long purchaseId, Status status, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getSeller()!=null){
            Purchase purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new RestApiException(CustomErrorCode.PURCHASE_NOT_FOUND));
            purchase.updateStatus(status);
            purchaseRepository.save(purchase);
        }else{
            throw new RuntimeException("User not found");
        }

    }

    public String showStatus(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PURCHASE_NOT_FOUND));
        String status = purchase.getStatus().name();
        return status;
    }
}
