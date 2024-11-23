package org.anyonetoo.anyonetoo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.anyonetoo.anyonetoo.domain.common.BaseEntity;
import org.anyonetoo.anyonetoo.domain.enums.Status;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "UpdateAlarm")
public class UpdateAlarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long updateAlarmId;

    @Column(nullable = false)
    private Long consumerId;

    @Column(nullable = false)
    private String productName;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String content;


    @Builder
    public UpdateAlarm(Long consumerId, String productName, Status status, String content) {
        this.consumerId = consumerId;
        this.productName = productName;
        this.status = status;
        this.content = content;
    }
}
