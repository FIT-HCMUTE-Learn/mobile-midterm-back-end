package com.mobile.api.mapper;

import com.mobile.api.dto.category.CategoryAdminDto;
import com.mobile.api.dto.category.CategoryDto;
import com.mobile.api.dto.user.UserAdminDto;
import com.mobile.api.dto.user.UserDto;
import com.mobile.api.form.category.CreateCategoryForm;
import com.mobile.api.form.category.UpdateCategoryForm;
import com.mobile.api.form.user.CreateUserAdminForm;
import com.mobile.api.form.user.CreateUserForm;
import com.mobile.api.form.user.UpdateUserAdminForm;
import com.mobile.api.form.user.UpdateUserForm;
import com.mobile.api.model.entity.Category;
import com.mobile.api.model.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateCategoryForm")
    Category fromCreateCategoryForm(CreateCategoryForm createCategoryForm);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("updateFromUpdateCategoryForm")
    void updateFromUpdateCategoryForm(@MappingTarget Category category, UpdateCategoryForm updateCategoryForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCategoryDto")
    CategoryDto fromEntityToCategoryDto(Category category);

    @IterableMapping(elementTargetType = CategoryDto.class, qualifiedByName = "fromEntityToCategoryDto")
    List<CategoryDto> fromEntitiesToCategoryDtoList(List<Category> categories);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToCategoryAdminDto")
    CategoryAdminDto fromEntityToCategoryAdminDto(Category category);

    @IterableMapping(elementTargetType = CategoryAdminDto.class, qualifiedByName = "fromEntityToCategoryAdminDto")
    List<CategoryAdminDto> fromEntitiesToCategoryAdminDtoList(List<Category> categories);
}
