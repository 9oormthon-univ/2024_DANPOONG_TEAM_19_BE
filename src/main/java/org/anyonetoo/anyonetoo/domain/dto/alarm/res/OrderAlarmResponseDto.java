package org.anyonetoo.anyonetoo.domain.dto.alarm.res;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderAlarmResponseDto {
    private String consumerName;
    private String productName;
}
