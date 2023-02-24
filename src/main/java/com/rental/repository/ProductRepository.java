package com.rental.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.rental.domain.Product;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
   List<Product> findByNameLike(String name);
}
