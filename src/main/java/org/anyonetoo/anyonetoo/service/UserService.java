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
import org.anyonetoo.anyonetoo.exception.RestApiException;
import org.anyonetoo.anyonetoo.exception.code.CustomErrorCode;
import org.anyonetoo.anyonetoo.jwt.JwtTokenProvider;
import org.anyonetoo.anyonetoo.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConsumerRepository consumerRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;
    private final SellerPreferRepository sellerPreferRepository;
    private final ConsumerPreferRepository consumerPreferRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void registerUser(RegisterRequestDTO request) {
        User user = User.builder()
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
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

    public String login(String userId, String userPassword) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(userPassword, user.getPassword())) {
            System.out.println(userPassword);
            System.out.println(user.getPassword());
            throw new RuntimeException("Invalid password");
        }
        System.out.println(userId);

        return jwtTokenProvider.createToken(userId);
    }

    @Transactional
    public void addSellerCategories(Long sellerId, List<Long> categoryIds) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.SELLER_NOT_FOUND));

        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new RestApiException(CustomErrorCode.CATEGORY_NOT_FOUND);
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
    public void addConsumerCategories(Long consumerId, List<Long> categoryIds) {
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new RestApiException(CustomErrorCode.CONSUMER_NOT_FOUND));

        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new RestApiException(CustomErrorCode.CATEGORY_NOT_FOUND);
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
