package com.mobile.api.form.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * PHAM KHANH HUY - 22110336
 */
/**
 * LIEN HUE TIEN - 22110433
 */
@Data
@Schema(description = "Update Product Form")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateProductForm {
    @Schema(description = "ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "ID cannot be null")
    private Long id;

    @Schema(description = "Name", example = "Spaghetti", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name can not be empty")
    private String name;

    @Schema(description = "Description", example = "Spaghetti", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Description can not be empty")
    private String description;

    @Schema(description = "Image", example = "/image", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "Image can not be empty")
    private String image;

    @Schema(description = "categoryId", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "categoryId can not be null")
    private Long categoryId;
}
