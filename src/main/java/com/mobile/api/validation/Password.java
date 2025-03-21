package com.mobile.api.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mobile.api.validation.impl.PasswordValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * LE HONG PHUC - 22110399
 */
/**
 * LIEN HUE TIEN - 22110433
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidation.class)
@Documented
public @interface Password {
    boolean allowNull() default false;

    int minLength() default 8;

    String message() default "Password must be at least 8 characters, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
