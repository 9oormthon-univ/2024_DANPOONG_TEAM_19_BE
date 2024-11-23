package org.anyonetoo.anyonetoo.service;


import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.mypage.res.StatusResponseDTO;
import org.anyonetoo.anyonetoo.domain.entity.Consumer;
import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.enums.Status;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.ConsumerRepository;
import org.anyonetoo.anyonetoo.repository.PurchaseRepository;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;
    private final ConsumerRepository consumerRepository;
    private final S3Service s3Service;

    public void updateStatus(Long purchaseId, Status status, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if(user.getSeller()!=null){
            Purchase purchase = purchaseRepository.findById(purchaseId)
                    .orElseThrow(() -> new RestApiException(CustomErrorCode.PURCHASE_NOT_FOUND));
            purchase.updateStatus(status);
            purchaseRepository.save(purchase);

            Consumer consumer = purchase.getConsumer();
            alarmService.createUpdateAlarm(consumer.getId(), purchase.getProduct().getTitle(), status);
        }else{
            throw new RuntimeException("User not found");
        }
    }

    public StatusResponseDTO showStatus(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.PURCHASE_NOT_FOUND));

        String thumbnailUrl = s3Service.getImageUrl(
                purchase.getProduct().getImages().get(0).getImageUrl()
        );

        return StatusResponseDTO.from(purchase, thumbnailUrl);
    }
}