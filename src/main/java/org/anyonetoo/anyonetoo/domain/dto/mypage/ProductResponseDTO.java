package org.anyonetoo.anyonetoo.domain.dto.mypage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDTO {

        private Long productId;
        private String title;
        private String content;
        private Long price;
        private Long sellerId;
        private Image images;



        public static ProductResponseDTO from(Product product) {
                Image image = (product.getImages() != null && !product.getImages().isEmpty())
                        ? product.getImages().get(0) : null;
                return ProductResponseDTO.builder()
                        .productId(product.getProductId())
                        .title(product.getTitle())
                        .content(product.getContent())
                        .price(product.getPrice())
                        .sellerId(product.getSeller().getId())
                        .images(image)
                        .build();
        }

}