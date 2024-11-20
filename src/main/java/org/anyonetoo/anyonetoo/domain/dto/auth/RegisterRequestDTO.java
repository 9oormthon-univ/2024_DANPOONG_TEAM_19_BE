package org.anyonetoo.anyonetoo.domain.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    private String id; // 사용자 ID
    private String password; // 비밀번호
    private String name;     // 이름
    private Long age;        // 나이
    private String role;     // 역할 (e.g., "consumer", "seller")
}