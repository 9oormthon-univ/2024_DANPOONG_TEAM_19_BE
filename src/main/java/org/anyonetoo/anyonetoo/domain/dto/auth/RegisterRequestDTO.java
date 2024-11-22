package org.anyonetoo.anyonetoo.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    @Schema(description = "로그인 아이디", example = "id123")
    @NotBlank
    private String id; // 사용자 ID

    @Schema(description = "패스워드", example = "id123")
    @NotBlank
    private String password; // 비밀번호

    @Schema(description = "닉네임", example = "카리나")
    @NotBlank
    private String name;     // 이름

    @Schema(description = "사용자 나이", example = "20")
    private Long age;        // 나이

    @Schema(description = "사용자 역할", example = "consumer")
    @NotBlank
    private String role;     // 역할 (e.g., "consumer", "seller")


}