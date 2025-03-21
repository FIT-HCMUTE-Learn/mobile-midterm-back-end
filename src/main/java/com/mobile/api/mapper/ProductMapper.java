package com.mobile.api.mapper;

import com.mobile.api.dto.product.ProductAdminDto;
import com.mobile.api.dto.product.ProductDto;
import com.mobile.api.form.product.CreateProductForm;
import com.mobile.api.form.product.UpdateProductForm;
import com.mobile.api.model.entity.Product;
import org.mapstruct.*;

import java.util.List;

/**
 * PHAM KHANH HUY - 22110336
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CategoryMapper.class})
public interface ProductMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateProductForm")
    Product fromCreateProductForm(CreateProductForm createProductForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @BeanMapping(ignoreByDefault = true)
    @Named("updateFromUpdateProductForm")
    void updateFromUpdateProductForm(@MappingTarget Product product, UpdateProductForm updateProductForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryDto")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductDto")
    ProductDto fromEntityToProductDto(Product product);

    @IterableMapping(elementTargetType = ProductDto.class, qualifiedByName = "fromEntityToProductDto")
    List<ProductDto> fromEntitiesToProductDtoList(List<Product> products);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToCategoryDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToProductAdminDto")
    ProductAdminDto fromEntityToProductAdminDto(Product product);

    @IterableMapping(elementTargetType = ProductAdminDto.class, qualifiedByName = "fromEntityToProductAdminDto")
    List<ProductAdminDto> fromEntitiesToProductAdminDtoList(List<Product> products);
}
