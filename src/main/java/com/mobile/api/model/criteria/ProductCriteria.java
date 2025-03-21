package com.mobile.api.model.criteria;

import com.mobile.api.model.criteria.base.BaseCriteria;
import com.mobile.api.model.entity.Category;
import com.mobile.api.model.entity.Product;
import jakarta.persistence.criteria.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * PHAM KHANH HUY - 22110336
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductCriteria extends BaseCriteria<Product> {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private Long categoryId;
    private String categoryName;

    @Override
    public Specification<Product> getSpecification() {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(getBaseSpecification().toPredicate(root, query, cb));

            if (StringUtils.hasText(getName())) {
                predicates.add(cb.like(cb.lower(root.get("action")), "%" + getName().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(getDescription())) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + getDescription().toLowerCase() + "%"));
            }
            if (getCategoryId() != null) {
                Join<Product, Category> categoryJoin = root.join("category", JoinType.INNER);
                predicates.add(cb.equal(categoryJoin.get("id"), getCategoryId()));
            }
            if (StringUtils.hasText(getCategoryName())) {
                Join<Product, Category> categoryJoin = root.join("category", JoinType.INNER);
                predicates.add(cb.equal(cb.lower(categoryJoin.get("name")), getCategoryName().toLowerCase()));
            }

            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
