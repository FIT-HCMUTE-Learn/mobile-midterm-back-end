package com.mobile.api.scheduler;

import com.mobile.api.repository.AccountRepository;
import com.mobile.api.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * LE HONG PHUC - 22110399
 */
@Component
public class OtpScheduler {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OtpRepository otpRepository;

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void cleanupExpiredOTPs() {
        accountRepository.deleteExpiredOTPs(LocalDateTime.now());
        otpRepository.deleteExpiredOTPs(LocalDateTime.now());
    }
}
