package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.ProductDTO;
public interface ProductService {

    ProductDTO save(ProductDTO productDTO);

   Optional<ProductDTO> update(ProductDTO productDTO);

    Page<ProductDTO> findAll(Pageable pageable);

    Optional<ProductDTO> findOne(Long id);

    void delete(Long id);
}
