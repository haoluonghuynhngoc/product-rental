package com.rental.service;

import java.util.List;
import java.util.Optional;

import com.rental.service.dto.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.ProductDTO;

public interface ProductService {

    ProductDTO save(ProductDTO productDTO);

    Optional<ProductDTO> update(ProductDTO productDTO);

    PagingResponse<ProductDTO> searchByName(String nameProduct, Pageable pageable);

    Page<ProductDTO> findAll(Pageable pageable);

  //  List<ProductDTO> findAllProduct();
    Optional<ProductDTO> findOne(Long id);
    List<ProductDTO> searchByNameId(String nameProduct);
    void delete(Long id);
}
