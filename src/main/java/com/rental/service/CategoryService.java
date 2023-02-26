package com.rental.service;

import java.util.Optional;

import com.rental.service.dto.CategoryShowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.CategoryDTO;

public interface CategoryService {
    CategoryDTO save(CategoryDTO categoryDTO);
    Optional<CategoryDTO> updateCategory(CategoryDTO categoryDTO);
    Page<CategoryDTO> findAll(Pageable pageable);
    Optional<CategoryShowDTO> findOne(Long id);
    void delete(Long id);
}
