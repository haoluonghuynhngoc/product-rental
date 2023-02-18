package com.rental.repository;

import com.rental.service.dto.ProductDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.rental.domain.Product;


@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
