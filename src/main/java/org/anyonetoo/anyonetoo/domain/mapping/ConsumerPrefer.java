package org.anyonetoo.anyonetoo.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.anyonetoo.anyonetoo.domain.common.BaseEntity;
import org.anyonetoo.anyonetoo.domain.entity.Category;
import org.anyonetoo.anyonetoo.domain.entity.Consumer;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Consumer_Prefer")
public class ConsumerPrefer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Consumer consumer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
