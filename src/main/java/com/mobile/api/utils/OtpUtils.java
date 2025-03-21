package com.mobile.api.utils;

import java.security.SecureRandom;

/**
 * LE HONG PHUC - 22110399
 */
public class OtpUtils {
    private static final SecureRandom random = new SecureRandom();

    public static String generateOTP(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}

