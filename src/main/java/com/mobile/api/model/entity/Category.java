package com.mobile.api.model.entity;

import com.mobile.api.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * PHAM KHANH HUY - 22110336
 */

@Entity
@Table(name = "db_mobile_midterm_category")
@Getter
@Setter
public class Category extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", type = com.mobile.api.service.id.IdGenerator.class)
    private Long id;
    private String name;
    @Column(name = "description" ,  columnDefinition = "TEXT")
    private String description;
    private String image;
    private Integer kind;
}
