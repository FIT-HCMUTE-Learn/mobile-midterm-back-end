package com.mobile.api.controller;

import com.mobile.api.controller.base.BaseController;
import com.mobile.api.dto.ApiMessageDto;
import com.mobile.api.dto.PaginationDto;
import com.mobile.api.dto.category.CategoryAdminDto;
import com.mobile.api.dto.category.CategoryDto;
import com.mobile.api.enumeration.ErrorCode;
import com.mobile.api.exception.ResourceBadRequestException;
import com.mobile.api.exception.ResourceNotFoundException;
import com.mobile.api.form.category.CreateCategoryForm;
import com.mobile.api.form.category.UpdateCategoryForm;
import com.mobile.api.mapper.CategoryMapper;
import com.mobile.api.model.criteria.CategoryCriteria;
import com.mobile.api.model.entity.Category;
import com.mobile.api.repository.AccountRepository;
import com.mobile.api.repository.CategoryRepository;
import com.mobile.api.repository.ProductRepository;
import com.mobile.api.repository.UserRepository;
import com.mobile.api.utils.ApiMessageUtils;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_LIS')")
    public ApiMessageDto<PaginationDto<CategoryAdminDto>> getListForAdmin(
            @Valid @ModelAttribute CategoryCriteria categoryCriteria,
            Pageable pageable
    ) {
        Page<Category> pageData = categoryRepository.findAll(categoryCriteria.getSpecification(), pageable);

        PaginationDto<CategoryAdminDto> responseDto = new PaginationDto<>(
                categoryMapper.fromEntitiesToCategoryAdminDtoList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

        return ApiMessageUtils.success(responseDto, "Get category list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_GET')")
    public ApiMessageDto<CategoryAdminDto> getForAdmin(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        return ApiMessageUtils.success(categoryMapper.fromEntityToCategoryAdminDto(category), "Get category successfully");
    }

    @GetMapping(value = "/client-get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CategoryDto> getForClient(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        return ApiMessageUtils.success(categoryMapper.fromEntityToCategoryDto(category), "Get category successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_CRE')")
    public ApiMessageDto<Void> create(
            @Valid @RequestBody CreateCategoryForm createCategoryForm,
            BindingResult bindingResult
    ) {
        if (categoryRepository.existsByName(createCategoryForm.getName())) {
            throw new ResourceBadRequestException(ErrorCode.CATEGORY_NAME_EXISTED);
        }
        // Create CATEGORY
        Category category = categoryMapper.fromCreateCategoryForm(createCategoryForm);
        categoryRepository.save(category);

        return ApiMessageUtils.success(null, "Create category successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_UDP')")
    public ApiMessageDto<Void> updateUser(
            @Valid @RequestBody UpdateCategoryForm updateCategoryForm,
            BindingResult bindingResult
    ) {
        Category category = categoryRepository.findById(updateCategoryForm.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        // Update name
        if (!category.getName().equals(updateCategoryForm.getName())) {
            if (categoryRepository.existsByName(updateCategoryForm.getName())) {
                throw new ResourceBadRequestException(ErrorCode.CATEGORY_NAME_EXISTED);
            }
        }
        // Update CATEGORY
        categoryMapper.updateFromUpdateCategoryForm(category, updateCategoryForm);
        categoryRepository.save(category);

        return ApiMessageUtils.success(null, "Update category successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('CAT_DEL')")
    public ApiMessageDto<Void> delete(@PathVariable Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));

        if (productRepository.existsByCategoryId(id)) {
            throw new ResourceBadRequestException(ErrorCode.CATEGORY_ERROR_CANT_DELETE_RELATIONSHIP_WITH_PRODUCT);
        }

        // Delete CATEGORY
        categoryRepository.deleteById(id);
        return ApiMessageUtils.success(null, "Delete category successfully");
    }
}
