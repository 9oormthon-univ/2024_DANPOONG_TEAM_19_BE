package org.anyonetoo.anyonetoo.service;


import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.anyonetoo.anyonetoo.domain.enums.Status;
import org.anyonetoo.anyonetoo.repository.AlarmRepository;
import org.anyonetoo.anyonetoo.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final PurchaseRepository purchaseRepository;
    private final AlarmRepository alarmRepository;

    public void updateStatus(Long purchaseId, Status status) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("구매없음"));
        purchase.updateStatus(status);
        purchaseRepository.save(purchase);

//        String consumer = purchase.getConsumer().getName();
//        String product = purchase.getProduct().getTitle();
//
//        if(status==ACCEPT){
//            Alarm alarm = Alarm.createAlarm(purchase, "구매를 수락하였습니다");
//            alarmRepository.save(alarm);
//        }
//        if(status==DEPOSIT){
//            Alarm alarm = Alarm.createAlarm(purchase, "입금이 확인되었습니다");
//            alarmRepository.save(alarm);
//        }
//        if(status==PRODUCING){
//            Alarm alarm = Alarm.createAlarm(purchase, "상품을 제작중입니다");
//            alarmRepository.save(alarm);
//        }
//        if(status==DELIVERY){
//            Alarm alarm = Alarm.createAlarm(purchase, "상품이 배송중입니다");
//            alarmRepository.save(alarm);
//        }
//        if(status==COMPLETE){
//            Alarm alarm = Alarm.createAlarm(purchase, "상품이 배송완료되었습니다");
//            alarmRepository.save(alarm);
//        }
    }

    public String showStatus(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new IllegalArgumentException("구매없음"));
        String status = purchase.getStatus().name();
        return status;
    }
}
