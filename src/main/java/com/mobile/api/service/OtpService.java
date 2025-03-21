package com.mobile.api.service;

import com.mobile.api.model.OtpCode;
import com.mobile.api.repository.OtpRepository;
import com.mobile.api.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * LE HONG PHUC - 22110399
 */
@Service
public class OtpService {
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private EmailService emailService;

    @Value("${otp.expiry.minutes.register}")
    private int otpExpiryMinutes;

    public void sendOTPEmail(String email) {
        String otp = OtpUtils.generateOTP(6);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(otpExpiryMinutes);

        OtpCode otpCode = new OtpCode();
        otpCode.setEmail(email);
        otpCode.setOtp(otp);
        otpCode.setExpiryTime(expiryTime);
        otpRepository.save(otpCode);

        emailService.sendOTPEmail("Your OTP Code for Registration", email, otp, otpExpiryMinutes);
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<OtpCode> otpCodeOpt = otpRepository.findByEmailAndOtp(email, otp);

        if (otpCodeOpt.isPresent() && otpCodeOpt.get().getExpiryTime().isAfter(LocalDateTime.now())) {
            otpRepository.delete(otpCodeOpt.get());
            return true;
        }

        return false;
    }
}

