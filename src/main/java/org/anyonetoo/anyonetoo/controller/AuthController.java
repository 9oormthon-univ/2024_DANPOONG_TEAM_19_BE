package org.anyonetoo.anyonetoo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.anyonetoo.anyonetoo.domain.dto.auth.LoginRequestDTO;
import org.anyonetoo.anyonetoo.domain.dto.auth.RegisterRequestDTO;
import org.anyonetoo.anyonetoo.domain.entity.User;
import org.anyonetoo.anyonetoo.repository.UserRepository;
import org.anyonetoo.anyonetoo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {
        userService.registerUser(request);
        return ResponseEntity.ok("Registration successful");
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkId(@RequestBody String userId){
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) { return ResponseEntity.ok("사용가능한 아이디입니다."); }
        return ResponseEntity.status(400).body("이미 사용하고 있는 아이디가 있습니다");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            String token = userService.login(loginRequestDTO.getId(), loginRequestDTO.getPassword());
            return ResponseEntity.ok(token);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid password");
        }
    }

}
