package org.anyonetoo.anyonetoo.domain.dto.mypage.res;

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
    private String imgUrl;

    public static StatusResponseDTO from(Purchase purchase, String preSignedUrl) {
        return StatusResponseDTO.builder()
                .status(purchase.getStatus())
                .consumerId(purchase.getConsumer().getId())
                .consumerName(purchase.getConsumer().getName())
                .imgUrl(preSignedUrl)
                .build();
    }
}
