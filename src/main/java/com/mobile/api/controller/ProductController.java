package com.mobile.api.controller;

import com.mobile.api.constant.BaseConstant;
import com.mobile.api.controller.base.BaseController;
import com.mobile.api.dto.ApiMessageDto;
import com.mobile.api.dto.PaginationDto;
import com.mobile.api.dto.product.ProductAdminDto;
import com.mobile.api.dto.product.ProductDto;
import com.mobile.api.enumeration.ErrorCode;
import com.mobile.api.exception.ResourceBadRequestException;
import com.mobile.api.exception.ResourceNotFoundException;
import com.mobile.api.form.product.CreateProductForm;
import com.mobile.api.form.product.UpdateProductForm;
import com.mobile.api.mapper.ProductMapper;
import com.mobile.api.model.criteria.ProductCriteria;
import com.mobile.api.model.entity.Category;
import com.mobile.api.model.entity.Product;
import com.mobile.api.repository.CategoryRepository;
import com.mobile.api.repository.ProductRepository;
import com.mobile.api.utils.ApiMessageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController extends BaseController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductMapper productMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_LIS')")
    public ApiMessageDto<PaginationDto<ProductAdminDto>> getListForAdmin(
            @Valid @ModelAttribute ProductCriteria productCriteria,
            Pageable pageable
    ) {
        Page<Product> pageData = productRepository.findAll(productCriteria.getSpecification(), pageable);

        PaginationDto<ProductAdminDto> responseDto = new PaginationDto<>(
                productMapper.fromEntitiesToProductAdminDtoList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

        return ApiMessageUtils.success(responseDto, "Get product list successfully");
    }

    @GetMapping(value = "/client-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<PaginationDto<ProductDto>> getListForClient(
            @Valid @ModelAttribute ProductCriteria productCriteria,
            Pageable pageable
    ) {
        Page<Product> pageData = productRepository.findAll(productCriteria.getSpecification(), pageable);

        PaginationDto<ProductDto> responseDto = new PaginationDto<>(
                productMapper.fromEntitiesToProductDtoList(pageData.getContent()),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );

        return ApiMessageUtils.success(responseDto, "Get product list successfully");
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_GET')")
    public ApiMessageDto<ProductAdminDto> getForAdmin(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        return ApiMessageUtils.success(productMapper.fromEntityToProductAdminDto(product), "Get product successfully");
    }

    @GetMapping(value = "/client-get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductDto> getForClient(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        return ApiMessageUtils.success(productMapper.fromEntityToProductDto(product), "Get product successfully");
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_CRE')")
    public ApiMessageDto<Void> create(
            @Valid @RequestBody CreateProductForm createProductForm,
            BindingResult bindingResult
    ) {
        if (productRepository.existsByNameAndStatus(createProductForm.getName(), BaseConstant.STATUS_ACTIVE)) {
            throw new ResourceBadRequestException(ErrorCode.PRODUCT_NAME_EXISTED);
        }
        Category category = categoryRepository.findById(createProductForm.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        // Create PRODUCT
        Product product = productMapper.fromCreateProductForm(createProductForm);
        product.setCategory(category);
        productRepository.save(product);

        return ApiMessageUtils.success(null, "Create product successfully");
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_UDP')")
    public ApiMessageDto<Void> update(
            @Valid @RequestBody UpdateProductForm updateProductForm,
            BindingResult bindingResult
    ) {
        Product product = productRepository.findById(updateProductForm.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        // Update name
        if (!product.getName().equals(updateProductForm.getName())) {
            if (productRepository.existsByNameAndStatus(updateProductForm.getName(), BaseConstant.STATUS_ACTIVE)) {
                throw new ResourceBadRequestException(ErrorCode.PRODUCT_NAME_EXISTED);
            }
        }
        productMapper.updateFromUpdateProductForm(product, updateProductForm);
        if (!product.getCategory().getId().equals(updateProductForm.getCategoryId())) {
            Category category = categoryRepository.findById(updateProductForm.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
            product.setCategory(category);
        }
        // Update PRODUCT
        productRepository.save(product);

        return ApiMessageUtils.success(null, "Update product successfully");
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PRO_DEL')")
    public ApiMessageDto<Void> delete(@PathVariable Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.PRODUCT_NOT_FOUND));

        // Delete CATEGORY
        productRepository.deleteById(id);
        return ApiMessageUtils.success(null, "Delete product successfully");
    }
}
