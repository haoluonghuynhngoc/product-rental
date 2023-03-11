package com.rental.repository;

import com.rental.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.rental.domain.OrderDetails;

import java.util.List;


@SuppressWarnings("unused")
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findAllByProduct(Product product);
}
