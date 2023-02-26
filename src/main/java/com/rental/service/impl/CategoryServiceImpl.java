package com.rental.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.rental.domain.Product;
import com.rental.repository.ProductRepository;
import com.rental.service.dto.CategoryShowDTO;
import com.rental.service.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rental.domain.Category;
import com.rental.repository.CategoryRepository;
import com.rental.service.CategoryService;
import com.rental.service.dto.CategoryDTO;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;


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

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryShowDTO> findOne(Long id) {
        return categoryRepository.findById(id).map(c -> {
            Set<ProductDTO> productDTO = new HashSet<>();
            for (Product product : c.getProducts()) {
                productDTO.add(modelMapper.map(product, ProductDTO.class));
            }
            return CategoryShowDTO.builder()
                    .id(c.getId())
                    .name(c.getName())
                    .products(productDTO)
                    .build();
        });
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
