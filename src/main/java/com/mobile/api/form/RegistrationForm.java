package com.mobile.api.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobile.api.validation.Password;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * LE HONG PHUC - 22110399
 */
@Data
@Schema(description = "Registration Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationForm {
    @Schema(description = "Email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Password (must be at least 8 characters, include uppercase, lowercase, digit, and special character)",
            example = "P@ssw0rd", requiredMode = Schema.RequiredMode.REQUIRED)
    @Password(allowNull = false)
    private String password;
}
