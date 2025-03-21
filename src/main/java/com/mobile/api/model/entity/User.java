package com.mobile.api.model.entity;

import com.mobile.api.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "db_mobile_midterm_user")
@Getter
@Setter
public class User extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = com.mobile.api.service.id.IdGenerator.class)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Column(name = "full_name")
    private String fullName;

    private Integer gender;

    private LocalDateTime birthday;
}
