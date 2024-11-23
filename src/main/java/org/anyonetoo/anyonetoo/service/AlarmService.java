package org.anyonetoo.anyonetoo.service;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.AlarmResponseDto;
import org.anyonetoo.anyonetoo.domain.entity.Alarm;
import org.anyonetoo.anyonetoo.domain.entity.Consumer;
import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.repository.AlarmRepository;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public Alarm createAlarm(Consumer consumer, Purchase purchase, String productTitle){
        Alarm alarm = Alarm.builder()
                .content(String.format("%s님이 [%s] 구매를 요청하였습니다.", consumer.getName(),productTitle))
                .purchase(purchase)
                .build();

        return alarmRepository.save(alarm);
    }

//    public List<AlarmResponseDto> getAllAlarms(String userId){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RestApiException(CustomErrorCode.USER_NOT_FOUND));
//
//        if(user.getConsumer() != null){
//            // 유저가 구매자일때
//            // 구매자로 등록된 구매 아이디를 가져요고
//            // 그 구매 아이디로 알람의 purchase에 접근해서
//            // 해당 구매 아이디로 등록된 알람을 가져오는
//        } else if (user.getSeller() != null) {
//            // 유저가 판매자일때
//            // 판매자로 등록되어 있는 상품의 아이디를 가져오고
//            //
//        }else{
//            throw new RestApiException(CustomErrorCode.INVALID_USER_TYPE);
//        }
//
//        return ;
//    }

}
