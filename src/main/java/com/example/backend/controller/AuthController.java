package com.example.backend.controller;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserLoginDto;
import com.example.backend.model.User;
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
    public ResponseEntity<User> signUp(@RequestBody UserDto dto) {
        System.out.println("dto" + dto.getEmail());
        return new ResponseEntity<>(authService.signUp(dto), HttpStatus.OK);
    }

    @PostMapping("/login" )
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) throws AuthenticationException {
        System.out.println(email + password);
        return new ResponseEntity<>(authService.login(email, password), HttpStatus.OK);
    }

    @PostMapping("/{email}/verify")
    public ResponseEntity<String> verifyOTP(@PathVariable String email, @RequestParam String otp) {
        System.out.println(email+otp);
        if(authService.verifyOTP(email, otp)) {
            return new ResponseEntity<>("Xác thực OTP thành công", HttpStatus.OK);
        }
        return new ResponseEntity<>("Xác thực OTP thất bại", HttpStatus.OK);
    }
}
