package com.mobile.api.form.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * PHAM KHANH HUY - 22110336
 */

@Data
@Schema(description = "Create Category Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateCategoryForm {
    @Schema(description = "Name", example = "Spaghetti", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name can not be empty")
    private String name;

    @Schema(description = "Description", example = "Spaghetti", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Description can not be empty")
    private String description;

    @Schema(description = "Image", example = "/image", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Image can not be empty")
    private String image;

    @Schema(description = "Kind", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Kind can not be null")
    private Integer kind;
}
