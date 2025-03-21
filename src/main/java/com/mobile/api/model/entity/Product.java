package com.mobile.api.model.entity;

import com.mobile.api.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "db_mobile_midterm_product")
@Getter
@Setter
public class Product extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = com.mobile.api.service.id.IdGenerator.class)
    private Long id;
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
