package com.rental.service.impl;

import java.util.*;

import com.rental.domain.Product;
import com.rental.domain.enums.ProductStatus;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rental.domain.Category;
import com.rental.repository.CategoryRepository;
import com.rental.service.CategoryService;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {
        return categoryRepository.findById(categoryDTO.getId()).map(
                existingCategory -> {
                    // mới sửa ở đây
//                    if (categoryDTO.getName().isEmpty()||categoryDTO.getName()==null)
//                        categoryDTO.setName(existingCategory.getName());
                    modelMapper.map(categoryDTO, existingCategory);
                    return existingCategory;
                }).map(categoryRepository::save).map(
                c -> {
                    return modelMapper.map(c, CategoryDTO.class);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(c -> {
            return modelMapper.map(c, CategoryDTO.class);
        });
    }

    //    @Override
//    @Transactional(readOnly = true)
//    public Optional<CategoryShowDTO> findOne(Long id) {
//        return categoryRepository.findById(id).map(c -> {
//            Set<ProductDTO> productDTO = new HashSet<>();
//            for (Product product : c.getProducts()) {
//                if (product.getStatus() != null) {
//                    if (!product.getStatus().equals(ProductStatus.RENTING)) {
//                        productDTO.add(modelMapper.map(product, ProductDTO.class));
//                    }
//                }
//            }
//            return CategoryShowDTO.builder()
//                    .id(c.getId())
//                    .name(c.getName())
//                    .products(productDTO)
//                    .build();
//        });
//    }
    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryShowDTO> findOne(Long id, Pageable pageable) {
        return categoryRepository.findById(id).map(c -> {
            List<ProductDTO> productDTO = new ArrayList<>();
            for (Product product : c.getProducts()) {
                if (product.getStatus() != null) {
                    if (product.getStatus().equals(ProductStatus.APPROVED)) {
                        productDTO.add(modelMapper.map(product, ProductDTO.class));
                    }
                }
            }
            Page<ProductDTO> pageProduct = new PageImpl<>(
                    productDTO, pageable, productDTO.size());
            return CategoryShowDTO.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .PageProducts(
                            PagingResponse.<ProductDTO>builder()
                                    .page(pageProduct.getPageable().getPageNumber() + 1)
                                    .size(pageProduct.getSize())
                                    .totalPage(pageProduct.getTotalPages())
                                    .totalItem(pageProduct.getTotalElements())
                                    // thuật toán phân trang
                                    .contends(pageProduct.getContent().subList(pageProduct.getPageable().getPageNumber() * pageProduct.getSize(),
                                            Math.min(pageProduct.getPageable().getPageNumber() * pageProduct.getSize() + pageProduct.getSize(), pageProduct.getContent().size())
                                    ))
                                    .build()
                    )
                    .build();
        });
    }


    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
