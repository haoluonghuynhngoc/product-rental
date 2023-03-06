package com.rental.repository;

import com.rental.domain.CartItems;
import com.rental.domain.Product;
import com.rental.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    List<CartItems> findAllByUser(User user);
    void removeCartItemsByUserAndProduct(User user, Product product);
    boolean existsByUserAndProduct(User user, Product product);
}
