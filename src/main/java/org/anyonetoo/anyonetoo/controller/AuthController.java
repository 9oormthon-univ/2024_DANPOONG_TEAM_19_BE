package org.anyonetoo.anyonetoo.controller;

import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.auth.LoginRequestDTO;
import org.anyonetoo.anyonetoo.domain.dto.auth.RegisterRequestDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.anyonetoo.anyonetoo.security.Encoder;
import org.anyonetoo.anyonetoo.security.JwtUtil;
import org.anyonetoo.anyonetoo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final Encoder encoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        try {
            String encryptedPassword = encoder.encrypt(request.getPassword());
            userService.register(request, encryptedPassword);
            return ResponseEntity.ok("회원가입 성공");
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(400).body("회원가입 실패");
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkId(@RequestBody String userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) { return ResponseEntity.ok("사용가능한 아이디입니다."); }
        return ResponseEntity.status(400).body("중복된 아이디가 있습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) throws NoSuchAlgorithmException {
        User user = userRepository.findById(loginRequestDTO.getId())
                .orElse(null);

        if (user == null || !encoder.encrypt(loginRequestDTO.getPassword()).equals(user.getPassword())) {
            return ResponseEntity.status(400).body("유효한 사용자가 없습니다.");
        }

        String token = jwtUtil.createToken(user.getId());
        return ResponseEntity.ok(token);
    }
}
