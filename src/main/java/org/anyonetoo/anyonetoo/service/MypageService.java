package org.anyonetoo.anyonetoo.service;


import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.mypage.ProductResponseDTO;
import org.anyonetoo.anyonetoo.domain.dto.mypage.StatusResponseDTO;
import org.anyonetoo.anyonetoo.domain.entity.Consumer;
import org.anyonetoo.anyonetoo.domain.entity.Product;
import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.enums.Status;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.AlarmRepository;
import org.anyonetoo.anyonetoo.repository.ConsumerRepository;
import org.anyonetoo.anyonetoo.repository.PurchaseRepository;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;
    private final ConsumerRepository consumerRepository;

    public void updateStatus(Long purchaseId, Status status, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Consumer consumer = consumerRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CONSUMER_NOT_FOUND));

        if(user.getSeller()!=null){
            Purchase purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new RestApiException(CustomErrorCode.PURCHASE_NOT_FOUND));
            purchase.updateStatus(status);
            purchaseRepository.save(purchase);

            // public UpdateAlarm createUpdateAlarm(Long consumerId, String productName, Status status)
            alarmService.createUpdateAlarm(consumer.getId(), purchase.getProduct().getTitle(), status);
        }else{
            throw new RuntimeException("User not found");
        }

    }

    public StatusResponseDTO showStatus(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PURCHASE_NOT_FOUND));

        return StatusResponseDTO.from(purchase);

    }
}
