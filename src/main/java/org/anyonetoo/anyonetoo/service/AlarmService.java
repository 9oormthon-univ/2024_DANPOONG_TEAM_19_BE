package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.alarm.AlarmResponseDto;
import org.anyonetoo.anyonetoo.domain.entity.*;
import org.anyonetoo.anyonetoo.domain.enums.Status;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.AlarmRepository;
import org.anyonetoo.anyonetoo.repository.UpdateAlarmRepository;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final UpdateAlarmRepository updateAlarmRepository;

    public List<AlarmResponseDto> getAlarms(User user){
        if(user.getSeller() != null) {
            return getAllOrderAlarm(user.getSeller().getId());
        }else if(user.getConsumer() != null){
            return getAllUpdateAlarm(user.getConsumer().getId());
        }else{
          throw new RestApiException(CustomErrorCode.USER_NOT_FOUND);
        }
    }

    public UpdateAlarm createUpdateAlarm(Long consumerId, String productName, Status status) {
        String content = switch (status) {
            case PAYMENT_PENDING -> "구매 요청이 수락되었습니다. 입금을 진행해주세요.";
            case PAYMENT_COMPLETE -> "입금이 확인되었습니다. 제작이 곧 시작됩니다.";
            case IN_PRODUCTION -> "제작이 시작되었습니다. 완성까지 조금만 기다려주세요.";
            case IN_DELIVERY -> "제품이 발송되었습니다. 배송 조회를 확인해주세요.";
            case DELIVERY_COMPLETE -> "제품 배송이 완료되었습니다. 구매 만족도를 평가해주세요.";
            default -> "주문 상태가 업데이트되었습니다.";
        };

        UpdateAlarm updateAlarm = UpdateAlarm.builder()
                .consumerId(consumerId)
                .productName(productName)
                .status(status)
                .content(content)
                .build();

        return updateAlarmRepository.save(updateAlarm);
    }

    public Alarm createOrderAlarm(String consumerName, Long sellerId, String productName) {
        Alarm alarm = Alarm.builder()
                .consumerName(consumerName)
                .sellerId(sellerId)
                .productName(productName)
                .build();

        return alarmRepository.save(alarm);
    }

    public List<AlarmResponseDto> getAllUpdateAlarm(Long consumerId) {
        return updateAlarmRepository.findAllByConsumerId(consumerId).stream()
                .map(AlarmResponseDto::from)
                .collect(Collectors.toList());
    }

    public List<AlarmResponseDto> getAllOrderAlarm(Long sellerId) {
        return alarmRepository.findAllBySellerId(sellerId).stream()
                .map(AlarmResponseDto::from)
                .collect(Collectors.toList());
    }
}
