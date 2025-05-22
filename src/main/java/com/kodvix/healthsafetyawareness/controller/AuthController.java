package com.kodvix.healthsafetyawareness.controller;


import com.kodvix.healthsafetyawareness.dto.*;
import com.kodvix.healthsafetyawareness.entity.User;
import com.kodvix.healthsafetyawareness.exception.*;
import com.kodvix.healthsafetyawareness.repository.AdminRepository;
import com.kodvix.healthsafetyawareness.service.EmailServiceImpl;
import com.kodvix.healthsafetyawareness.service.JwtService;
import com.kodvix.healthsafetyawareness.util.OtpData;
import com.kodvix.healthsafetyawareness.util.OtpStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor


public class AuthController {

    private final AdminRepository adminRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final OtpStore otpStore;

    
    // REGISTER ENDPOINT
    
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequestDto request) {
        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        adminRepository.save(user);

        return request.getRole().name() + " registered successfully";
    }

    // LOGIN ENDPOINT
  
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        User user = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email not registered. Please register first."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole());
        return new LoginResponseDto(token, user.getRole());
    }

    // FORGOT PASSWORD - SEND OTP
   
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody ForgotPasswordRequestDto request) {
        if (!request.getEmail().equals(request.getRepeatEmail())) {
            throw new PasswordMismatchException("Emails do not match");
        }

        User user = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.storeOtp(request.getEmail(), otp);
        emailService.sendOtp(user.getEmail(), otp);

        return "OTP sent to email";
    }

    // VERIFY OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpRequestDto request) {
        User user = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        OtpData data = otpStore.getOtpData(request.getEmail());
        if (data == null || !data.getOtp().equals(request.getOtp())) {
            throw new InvalidOtpException("Invalid OTP");
        }

        if (Duration.between(data.getRequestedTime(), LocalDateTime.now()).toMinutes() > 5) {
            throw new OtpExpiredException("OTP expired");
        }

        user.setIsOtpVerified(true);
        adminRepository.save(user);
        otpStore.removeOtp(request.getEmail());

        return "OTP verified successfully";
    }

    // RESEND OTP
   
    @PostMapping("/resend-otp")
    public String resendOtp(@RequestBody ResendOtpRequestDto request) {
        User user = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        OtpData existingOtp = otpStore.getOtpData(request.getEmail());
        if (existingOtp != null) {
            long minutes = Duration.between(existingOtp.getRequestedTime(), LocalDateTime.now()).toMinutes();
            if (minutes < 5) {
                throw new InvalidOtpException("Please wait " + (5 - minutes) + " minute(s) before requesting a new OTP.");
            }
        }

        String newOtp = String.format("%06d", new Random().nextInt(999999));
        otpStore.storeOtp(request.getEmail(), newOtp);
        user.setIsOtpVerified(false);
        adminRepository.save(user);
        emailService.sendOtp(user.getEmail(), newOtp);

        return "New OTP sent to email";
    }

    //RESET PASSWORD
   
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequestDto request) {
        User user = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        if (!Boolean.TRUE.equals(user.getIsOtpVerified())) {
            throw new InvalidOtpException("Please verify OTP first");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setIsOtpVerified(false);
        user.setOtp(null);
        user.setOtpRequestedTime(null);
        adminRepository.save(user);

        return "Password reset successful";
    }

}