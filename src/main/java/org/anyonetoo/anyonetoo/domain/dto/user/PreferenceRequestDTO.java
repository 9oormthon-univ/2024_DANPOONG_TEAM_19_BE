package org.anyonetoo.anyonetoo.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreferenceRequestDTO {

    @Schema(description = "카테고리 선택", example = "[1, 2, 3]")
    private List<Long> categoryIds;
}
