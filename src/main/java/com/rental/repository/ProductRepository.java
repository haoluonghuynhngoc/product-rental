package com.rental.repository;

import com.rental.domain.OrderDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import com.rental.domain.Product;

import java.util.List;
import java.util.stream.Stream;


@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
   List<Product> findByNameLikeOrId(String name,Long id);
   //Stream<Product> findAllByOrderDetails(OrderDetails orderDetails);
}
