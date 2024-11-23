package org.anyonetoo.anyonetoo.domain.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.anyonetoo.anyonetoo.domain.enums.Status;
@Getter
@Builder
@Setter
@AllArgsConstructor
public class StatusResponseDTO {

    private Status status;
    private Long consumerId;
    private String consumerName;

    public static StatusResponseDTO from(Purchase purchase) {
        return StatusResponseDTO.builder()
                .status(purchase.getStatus())
                .consumerId(purchase.getConsumer().getId())
                .consumerName(purchase.getConsumer().getName())
                .build();
    }
}
