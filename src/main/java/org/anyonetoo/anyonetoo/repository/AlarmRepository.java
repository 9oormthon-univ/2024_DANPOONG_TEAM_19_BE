package org.anyonetoo.anyonetoo.repository;

import org.anyonetoo.anyonetoo.domain.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
