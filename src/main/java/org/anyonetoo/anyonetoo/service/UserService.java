package org.anyonetoo.anyonetoo.service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.auth.RegisterRequestDTO;
import org.anyonetoo.anyonetoo.domain.entity.Category;
import org.anyonetoo.anyonetoo.domain.entity.Consumer;
import org.anyonetoo.anyonetoo.domain.entity.Seller;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.domain.mapping.ConsumerPrefer;
import org.anyonetoo.anyonetoo.domain.mapping.SellerPrefer;
import org.anyonetoo.anyonetoo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ConsumerRepository consumerRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final SellerPreferRepository sellerPreferRepository;
    private final ConsumerPreferRepository consumerPreferRepository;

    public void register(RegisterRequestDTO request, String encryptedPassword) {
        User user = User.builder()
                .id(request.getId())
                .password(encryptedPassword)
                .build();
        userRepository.save(user);
        // Consumer 또는 Seller 생성
        if ("consumer".equalsIgnoreCase(request.getRole())) {
            Consumer consumer = Consumer.builder()
                    .user(user)
                    .name(request.getName())
                    .age(request.getAge())
                    .build();
            consumerRepository.save(consumer);
        } else if ("seller".equalsIgnoreCase(request.getRole())) {
            Seller seller = Seller.builder()
                    .user(user)
                    .name(request.getName())
                    .age(request.getAge())
                    .build();
            sellerRepository.save(seller);
        }
    }

    @Transactional
    public void addSellerCategories(Long sellerId, List<Long> categoryIds) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("seller 찾을 수 없음"));

        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new RuntimeException("카테고리 목록 없음");
        }

        for (Category category : categories) {
            SellerPrefer sellerPrefer = SellerPrefer.builder()
                    .seller(seller)
                    .category(category)
                    .build();
            sellerPreferRepository.save(sellerPrefer);
        }
    }

    @Transactional
    public void addConsumerCategories(Long consumerId, List<Long> categoryIds){
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new RuntimeException("consumer 없음"));

        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new RuntimeException("seller 찾을 수 없음");
        }

        for (Category category : categories) {
            ConsumerPrefer consumerPrefer = ConsumerPrefer.builder()
                    .consumer(consumer)
                    .category(category)
                    .build();
            consumerPreferRepository.save(consumerPrefer);
        }
    }
}
