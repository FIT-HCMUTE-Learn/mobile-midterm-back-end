package com.mobile.api.dto.user;

import com.mobile.api.dto.account.AccountDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * LE HONG PHUC - 22110399
 */
@Data
public class UserDto {
    @Schema(description = "account")
    private AccountDto account;

    @Schema(description = "fullName")
    private String fullName;

    @Schema(description = "gender")
    private Integer gender;

    @Schema(description = "birthday")
    private LocalDateTime birthday;
}
