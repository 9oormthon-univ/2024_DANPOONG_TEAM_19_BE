package org.anyonetoo.anyonetoo.repository;

import org.anyonetoo.anyonetoo.domain.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    @Query("SELECT c FROM Consumer c WHERE c.user.userId = :userId")
    Optional<Consumer> findByUserId(@Param("userId") Long userId);
}
