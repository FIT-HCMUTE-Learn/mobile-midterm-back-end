package com.mobile.api.controller.oauth;

import com.mobile.api.dto.ApiMessageDto;
import com.mobile.api.enumeration.ErrorCode;
import com.mobile.api.exception.BusinessException;
import com.mobile.api.exception.ResourceNotFoundException;
import com.mobile.api.form.RegistrationForm;
import com.mobile.api.form.ResendOtpForm;
import com.mobile.api.form.VerifyOtpForm;
import com.mobile.api.model.entity.Account;
import com.mobile.api.repository.AccountRepository;
import com.mobile.api.service.OtpService;
import com.mobile.api.service.RegistrationService;
import com.mobile.api.security.ClientRegistrationService;
import com.mobile.api.utils.ApiMessageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * LE HONG PHUC - 22110399
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegistrationController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private ClientRegistrationService clientRegistrationService;
    
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<String> registerUser(
            @Valid @RequestBody RegistrationForm registrationForm,
            BindingResult bindingResult
    ) {
        registrationService.register(registrationForm);
        clientRegistrationService.registerClientForUser(registrationForm.getEmail(), registrationForm.getPassword());
        otpService.sendOTPEmail(registrationForm.getEmail());

        return ApiMessageUtils.success(null, "OTP send to email: " + registrationForm.getEmail());
    }

    @PostMapping(value = "/verify-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyOtp(
            @Valid @RequestBody VerifyOtpForm verifyOtpForm,
            BindingResult bindingResult
    ) {
        boolean isValid = otpService.verifyOtp(verifyOtpForm.getEmail(), verifyOtpForm.getOtp());

        if (!isValid) {
            throw new BusinessException(ErrorCode.BUSINESS_INVALID_OTP);
        }
        Account account = accountRepository.findByEmail(verifyOtpForm.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND));
        account.setVerified(true);
        accountRepository.save(account);

        return ResponseEntity.ok("Verified successfully!");
    }

    @GetMapping(value = "/resend-otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> resendOtp(
            @Valid @RequestBody ResendOtpForm resendOtpForm,
            BindingResult bindingResult
    ) {
        otpService.sendOTPEmail(resendOtpForm.getEmail());
        return ApiMessageUtils.success(null, "OTP resend to email: " + resendOtpForm.getEmail());
    }
}
