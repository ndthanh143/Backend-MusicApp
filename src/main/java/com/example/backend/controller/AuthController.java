package com.example.backend.controller;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserLoginDto;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private final AuthService authService;
    private final UserRepository userRepository;


    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto dto) {
        authService.signUp(dto);
        return new ResponseEntity<>("Đăng ký thành công!", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signUp(@RequestBody UserLoginDto dto) throws AuthenticationException {
        return new ResponseEntity<>(authService.login(dto), HttpStatus.OK);
    }

    @PostMapping("/{email}/verify")
    public ResponseEntity<String> verifyOTP(@PathVariable String email, @RequestParam String otp) {
        if(authService.verifyOTP(email, otp)) {
            return new ResponseEntity<>("Xác thực OTP thành công", HttpStatus.OK);
        }
        return new ResponseEntity<>("Xác thực OTP thất bại", HttpStatus.OK);
    }
}
