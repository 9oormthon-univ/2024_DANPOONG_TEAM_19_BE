package org.anyonetoo.anyonetoo.domain.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.anyonetoo.anyonetoo.domain.Category;
import org.anyonetoo.anyonetoo.domain.Seller;

@Getter
@Setter
public class PreferenceResponseDTO {
    private Long prefer_id;
    private Category category;
    private Seller seller;
}
