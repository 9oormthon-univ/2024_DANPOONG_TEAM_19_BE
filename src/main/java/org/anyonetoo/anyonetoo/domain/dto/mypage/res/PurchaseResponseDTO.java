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
public class PurchaseResponseDTO {

    private Long purchaseId;
    private Status status;
    private Long consumerId;
    private String consumerName;
    private String title;
    private String imgUrl;

    public static PurchaseResponseDTO from(Purchase purchase, String preSignedUrl) {
        return PurchaseResponseDTO.builder()
                .purchaseId(purchase.getPurchaseId())
                .status(purchase.getStatus())
                .consumerId(purchase.getConsumer().getId())
                .consumerName(purchase.getConsumer().getName())
                .title(purchase.getProduct().getTitle())
                .imgUrl(preSignedUrl)
                .build();
    }

}
