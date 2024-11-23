package org.anyonetoo.anyonetoo.domain.dto.alarm;

import lombok.Builder;
import org.anyonetoo.anyonetoo.domain.entity.Alarm;
import org.anyonetoo.anyonetoo.domain.entity.UpdateAlarm;

@Builder
public class AlarmResponseDto {
    String content1;
    String content2;

    public static AlarmResponseDto from(UpdateAlarm alarm) {
        return AlarmResponseDto.builder()
                .content1(alarm.getProductName())
                .content2(alarm.getContent())
                .build();
    }

    public static AlarmResponseDto from(Alarm alarm) {
        return AlarmResponseDto.builder()
                .content1(alarm.getConsumerName())
                .content2(alarm.getProductName())
                .build();
    }
}
