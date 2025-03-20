package com.mobile.api.model.criteria;

import com.mobile.api.model.criteria.base.BaseCriteria;
import com.mobile.api.model.entity.Category;
import com.mobile.api.model.entity.Permission;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryCriteria extends BaseCriteria<Category> {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private Integer kind;

    @Override
    public Specification<Category> getSpecification() {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(getBaseSpecification().toPredicate(root, query, cb));

            if (StringUtils.hasText(getName())) {
                predicates.add(cb.like(cb.lower(root.get("action")), "%" + getName().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(getDescription())) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + getDescription().toLowerCase() + "%"));
            }
            if (getKind() != null) {
                predicates.add(cb.equal(root.get("kind"), getKind()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
