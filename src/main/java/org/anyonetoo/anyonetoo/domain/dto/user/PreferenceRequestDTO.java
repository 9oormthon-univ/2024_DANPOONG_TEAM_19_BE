package org.anyonetoo.anyonetoo.domain.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreferenceRequestDTO {
    private List<Long> categoryIds;
}
