package com.rental.service;


import com.rental.service.dto.CartItemsDTO;
import com.rental.service.dto.NotificationDTO;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface CartItemsService {
    CartItemsDTO saveCart(CartItemsDTO cartItemsDTO);
    List<CartItemsDTO> findAll(Long id);
    Optional<CartItemsDTO> updateCartItem(CartItemsDTO cartItemsDTO);
    void delete(Long id);
}
