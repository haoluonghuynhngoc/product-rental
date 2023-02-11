package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.ProductDTO;

/**
 * Service Interface for managing {@link com.swp391.domain.Product}.
 */
public interface ProductService {

    ProductDTO save(ProductDTO productDTO);

    ProductDTO update(ProductDTO productDTO);

    Optional<ProductDTO> partialUpdate(ProductDTO productDTO);

    Page<ProductDTO> findAll(Pageable pageable);

    Optional<ProductDTO> findOne(Long id);

    void delete(Long id);
}
