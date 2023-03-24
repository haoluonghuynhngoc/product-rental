package com.rental.repository;

import com.rental.domain.Product;
import com.rental.domain.User;
import com.rental.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.rental.domain.Order;

import java.util.List;
import java.util.stream.Stream;


@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
    Stream<Order> findAllByStatus(OrderStatus status);
}
