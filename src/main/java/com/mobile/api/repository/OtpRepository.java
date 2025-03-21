package com.mobile.api.repository;

import com.mobile.api.model.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * LE HONG PHUC - 22110399
 */
@Repository
public interface OtpRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findByEmailAndOtp(String email, String otp);

    @Modifying
    @Query("UPDATE OtpCode oc SET oc.otp = NULL, oc.expiryTime = NULL WHERE oc.expiryTime < :now")
    void deleteExpiredOTPs(@Param("now") LocalDateTime now);
}
