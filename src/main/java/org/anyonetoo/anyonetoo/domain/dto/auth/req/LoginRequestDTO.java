package org.anyonetoo.anyonetoo.domain.dto.auth.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    @Schema(description = "로그인 아이디", example = "id123")
    @NotBlank(message = "아이디가 입력되지 않았습니다.")
    private String id;

    @Schema(description = "패스워드", example = "password123")
    @NotBlank(message = "패스워드가 입력되지 않았습니다.")
    private String password;
}
