package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.CategoryDTO;

public interface CategoryService {

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO);

    Optional<CategoryDTO> partialUpdate(CategoryDTO categoryDTO);

    Page<CategoryDTO> findAll(Pageable pageable);

    Optional<CategoryDTO> findOne(Long id);

    void delete(Long id);
}
