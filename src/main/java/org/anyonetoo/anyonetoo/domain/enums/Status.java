package org.anyonetoo.anyonetoo.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ORDER_REQUEST("구매 요청"),
    PAYMENT_PENDING("구매 수락"),
    PAYMENT_COMPLETE("입금 확인"),
    IN_PRODUCTION("제작중"),
    IN_DELIVERY("배송중"),
    DELIVERY_COMPLETE("배송 완료");

    private final String orderStatus;
}
