package org.anyonetoo.anyonetoo.repository;

import org.anyonetoo.anyonetoo.domain.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
