package org.anyonetoo.anyonetoo.domain.dto.user.res;

import lombok.Getter;
import lombok.Setter;
import org.anyonetoo.anyonetoo.domain.entity.Category;
import org.anyonetoo.anyonetoo.domain.entity.Seller;

@Getter
@Setter
public class PreferenceResponseDTO {
    private Long prefer_id;
    private Category category;
    private Seller seller;
}
