package com.mobile.api.exception;

import com.mobile.api.enumeration.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceBadRequestException extends RuntimeException {
    private final ErrorCode errorCode;

    public ResourceBadRequestException(String message) {
        super(message);
        this.errorCode = ErrorCode.SYSTEM_RESOURCE_NOT_FOUND;
    }

    public ResourceBadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage(), null, false, false); // Disable Stack trace
        this.errorCode = errorCode;
    }
}
