package org.anyonetoo.anyonetoo.repository;

import org.anyonetoo.anyonetoo.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTitleContaining(String title);
    List<Product> findBySellerId(Long sellerId);
}
