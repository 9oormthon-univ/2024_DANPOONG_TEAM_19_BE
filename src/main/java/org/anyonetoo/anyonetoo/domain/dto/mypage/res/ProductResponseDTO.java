package org.anyonetoo.anyonetoo.domain.dto.mypage.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.*;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDTO {
        private Long productId;
        private String title;
        private String content;
        private Long price;
        private Long sellerId;
        private String imageUrl;

        public static ProductResponseDTO from(Product product) {
                Image image = (product.getImages() != null && !product.getImages().isEmpty())
                        ? product.getImages().get(0) : null;
                return ProductResponseDTO.builder()
                        .productId(product.getProductId())
                        .title(product.getTitle())
                        .content(product.getContent())
                        .price(product.getPrice())
                        .sellerId(product.getSeller().getId())
                        .imageUrl(image.getImageUrl())
                        .build();
        }

}