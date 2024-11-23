package org.anyonetoo.anyonetoo.domain.dto.alarm.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.anyonetoo.anyonetoo.domain.entity.Alarm;
import org.anyonetoo.anyonetoo.domain.entity.UpdateAlarm;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "알람 응답 DTO(판매자/구매자)")
public class AlarmResponseDto {

    @Schema(description = "구매를 요청한 사람 / 구매한 상품")
    String content1;

    @Schema(description = "구매할 상품 / 변경된 상태에 대한 content ")
    String content2;

    public static AlarmResponseDto from(Alarm alarm) {
        return AlarmResponseDto.builder()
                .content1(alarm.getConsumerName())
                .content2(alarm.getProductName())
                .build();
    }

    public static AlarmResponseDto from(UpdateAlarm alarm) {
        return AlarmResponseDto.builder()
                .content1(alarm.getProductName())
                .content2(alarm.getContent())
                .build();
    }
}
