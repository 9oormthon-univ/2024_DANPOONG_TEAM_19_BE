package org.anyonetoo.anyonetoo.repository;

import org.anyonetoo.anyonetoo.domain.entity.UpdateAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UpdateAlarmRepository extends JpaRepository<UpdateAlarm, Long> {
    List<UpdateAlarm> findAllByConsumerId(Long consumerId);
}
