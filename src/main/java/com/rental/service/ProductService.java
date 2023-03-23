package com.rental.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.rental.domain.enums.OrderStatus;
import com.rental.domain.enums.ProductStatus;
import com.rental.domain.enums.UserStatus;
import com.rental.service.dto.OrderShowDTO;
import com.rental.service.dto.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.ProductDTO;

public interface ProductService {

    ProductDTO save(ProductDTO productDTO);

    Optional<ProductDTO> update(ProductDTO productDTO);

    PagingResponse<ProductDTO> searchByName(String nameProduct, Pageable pageable,Long id);

    Page<ProductDTO> findAll(Pageable pageable);
    Optional<ProductDTO> updateStatus(ProductStatus status, Long id);
  //  List<ProductDTO> findAllProduct();
    Optional<ProductDTO> findOne(Long id);
    List<ProductDTO> searchByNameId(String nameProduct);
    PagingResponse<ProductDTO> randomProduct(Pageable pageable);
    void delete(Long id);
}
