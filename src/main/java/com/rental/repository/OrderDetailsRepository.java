package com.rental.repository;

import com.rental.domain.Order;
import com.rental.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.rental.domain.OrderDetails;

import java.util.List;
import java.util.Optional;


@SuppressWarnings("unused")
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findAllByProduct(Product product);
    List<OrderDetails>  findAllByOrder(Order order);
   void deleteAllByProduct(Product product);
}
