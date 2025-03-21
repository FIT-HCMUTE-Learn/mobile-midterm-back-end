package com.mobile.api.model;

import com.mobile.api.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * LE HONG PHUC - 22110399
 */
@Entity
@Table(name = "otp_codes")
@Getter
@Setter
public class OtpCode extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = com.mobile.api.service.id.IdGenerator.class)
    private Long id;

    private String email;

    private String otp;

    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;
}
