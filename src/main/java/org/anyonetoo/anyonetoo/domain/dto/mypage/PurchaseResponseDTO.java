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
public class PurchaseResponseDTO {

    private Status status;
    private Long consumerId;

    public static PurchaseResponseDTO from(Purchase purchase) {
        return PurchaseResponseDTO.builder()
                .status(purchase.getStatus())
                .consumerId(purchase.getConsumer().getId())
                .build();
    }


}
