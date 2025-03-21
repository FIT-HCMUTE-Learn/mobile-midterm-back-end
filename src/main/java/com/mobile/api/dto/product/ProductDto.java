package com.mobile.api.dto.product;

import com.mobile.api.dto.category.CategoryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductDto {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    private String name;

    @Schema(description = "description")
    private String description;

    @Schema(description = "image")
    private String image;

    @Schema(description = "category")
    private CategoryDto category;
}
