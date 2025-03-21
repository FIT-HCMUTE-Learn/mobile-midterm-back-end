package com.mobile.api.dto.category;

import com.mobile.api.dto.BaseAdminDto;
import com.mobile.api.dto.account.AccountAdminDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * PHAM KHANH HUY - 22110336
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryAdminDto extends BaseAdminDto {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "name")
    private String name;

    @Schema(description = "description")
    private String description;

    @Schema(description = "image")
    private String image;

    @Schema(description = "kind")
    private Integer kind;

    @Schema(description = "createdDate")
    private LocalDateTime createdDate;

    @Schema(description = "modifiedDate")
    private LocalDateTime modifiedDate;
}
