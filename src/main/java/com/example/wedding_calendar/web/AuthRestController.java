package com.example.wedding_calendar.web;

import com.example.wedding_calendar.dto.LoginRequestDto;
import com.example.wedding_calendar.dto.LoginResponseDto;
import com.example.wedding_calendar.entity.User;
import com.example.wedding_calendar.repository.UserRepository;
import com.example.wedding_calendar.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthRestController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 로그인(JWT 발급)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        Optional<User> optionalUser = userRepository.findById(request.getId());

        if(optionalUser.isEmpty() || !passwordEncoder.matches(request.getPw(), optionalUser.get().getPw())) {
            return ResponseEntity.status(401).body("비밀번호 오류");
        }

        String token = jwtTokenProvider.createToken(optionalUser.get().getId());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }
}
