package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.entity.Alarm;
import org.anyonetoo.anyonetoo.domain.entity.Consumer;
import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.anyonetoo.anyonetoo.repository.AlarmRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public Alarm createAlarm(Consumer consumer, Purchase purchase, String productTitle){
        Alarm alarm = Alarm.builder()
                .content(String.format("%s님이 [%s] 구매를 요청하였습니다.", consumer.getName(),productTitle))
                .purchase(purchase)
                .build();

        return alarmRepository.save(alarm);
    }
}
