package com.mobile.api.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * LE HONG PHUC - 22110399
 */
@Data
@Schema(description = "Verify OTP Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyOtpForm {
    @Schema(description = "Email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "OTP", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "OTP cannot be empty")
    private String otp;
}
