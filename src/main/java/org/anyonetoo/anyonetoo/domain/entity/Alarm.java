package org.anyonetoo.anyonetoo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.anyonetoo.anyonetoo.domain.common.BaseEntity;


// 구매 요청 알림
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Alarm")
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmId;

    @Column(nullable = false)
    private String consumerName;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private String productName;

    @Builder
    public Alarm(String consumerName, Long sellerId, String productName) {
        this.consumerName = consumerName;
        this.sellerId = sellerId;
        this.productName = productName;
    }
}
