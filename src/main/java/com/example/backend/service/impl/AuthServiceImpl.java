package com.example.backend.service.impl;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserLoginDto;
import com.example.backend.exception.InvalidException;
import com.example.backend.model.Role;
import com.example.backend.model.User;
import com.example.backend.model.UserOTP;
import com.example.backend.repository.UserOTPRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthService;
import com.example.backend.utils.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.naming.AuthenticationException;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository repo;
    private final UserOTPRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    public AuthServiceImpl(UserRepository repo, UserOTPRepository otpRepository) {
        this.repo = repo;
        this.otpRepository = otpRepository;
    }

    @Override
    public void signUp(UserDto dto) {
        Role role = new Role();
        role.setName(ERole.ROLE_USER);
        if(ObjectUtils.isEmpty(dto.getName())){
            throw new InvalidException("Tên tài khoản không được bỏ trống");
        }
        if(ObjectUtils.isEmpty(dto.getEmail())){
            throw new InvalidException("Địa chỉ email không được bỏ trống");

        }
        if(ObjectUtils.isEmpty(dto.getPassword())){
            throw new InvalidException("Mật khẩu không được để trống");
        }
        if(ObjectUtils.isEmpty(dto.getPhone())){
            throw new InvalidException("Số điện thoại không đươc bỏ trống");
        }
        if(ObjectUtils.isEmpty(role)){
            throw new InvalidException("Role không được bỏ trống");

        }
        if(repo.checkEmail(dto.getEmail().trim())){
            throw new InvalidException(String.format("Địa chỉ email: %s đã tồn tại",dto.getEmail()));
        }

        // Tạo mã xác thực
        String verificationCode = generateVerificationCode();
        UserOTP userOTP = new UserOTP();
        userOTP.setEmail(dto.getEmail());
        userOTP.setOtp(verificationCode);
        userOTP.setVerified(false);
        otpRepository.save(userOTP);


        User user = new User();
        user.setName(dto.getName().trim());
        user.setEmail(dto.getEmail().trim());
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone().trim());
        user.setRole(role);
        user.setTrangThai(false);
        repo.save(user);

        // Gửi email xác thực
        sendVerificationEmail(user.getEmail(), verificationCode);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int verificationCode = random.nextInt(900000) + 100000;
        return String.valueOf(verificationCode);
    }

    public boolean verifyOTP(String email, String otp) {
        Optional<UserOTP> userOTP = otpRepository.findByEmail(email);
        if (userOTP != null && userOTP.get().getOtp().equals(otp) && !userOTP.get().isVerified()) {
            // Xác thực thành công
            userOTP.get().setVerified(true);
            otpRepository.save(userOTP.get());
            Optional<User> user = repo.getUserByEmail(email);
            user.get().setTrangThai(true);
            repo.save(user.get());
            return true;
        }
        return false;
    }

    private void sendVerificationEmail(String email, String verificationCode) {
        // Tạo nội dung email xác thực
        String emailContent = "Your verification code: " + verificationCode;

        // Gửi email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Verificate Account");
        mailMessage.setText(emailContent);
        mailSender.send(mailMessage);
    }

    @Override
    public User login(UserLoginDto dto) throws AuthenticationException {
            Optional<User> user = repo.getUserByEmail(dto.getUsername());
        if(user == null) {
            throw new AuthenticationException("User not found");
        }
        if(user.get().getPassword() != dto.getPassword()) {
            throw new AuthenticationException("Password is Wrong!");
        }
        return user.get();
    }
}
