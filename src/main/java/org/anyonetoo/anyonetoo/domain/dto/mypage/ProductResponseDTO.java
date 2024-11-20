package org.anyonetoo.anyonetoo.domain.dto.mypage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.anyonetoo.anyonetoo.domain.entity.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponseDTO {
        private String title;
        private String content;
        private Long price;
        private Seller seller;
        private List<Image> images;

        public static ProductResponseDTO from(Product product) {
            return ProductResponseDTO.builder()
                    .title(product.getTitle())
                    .content(product.getContent())
                    .price(product.getPrice())
                    .seller(product.getSeller())
                    .images(product.getImages())
                    .build();
        }

}
