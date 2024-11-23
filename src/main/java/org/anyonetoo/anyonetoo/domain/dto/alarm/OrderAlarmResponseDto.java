package org.anyonetoo.anyonetoo.domain.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderAlarmResponseDto {
    private String consumerName;
    private String productName;
}
