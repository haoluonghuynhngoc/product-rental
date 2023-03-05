package com.rental.service;

import java.util.List;
import java.util.Optional;

import com.rental.domain.User;
import com.rental.service.dto.OrderShowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.OrderDTO;

public interface OrderService {

    OrderDTO save(OrderDTO orderDTO);

    Optional<OrderDTO> update(OrderDTO orderDTO);

    Page<OrderShowDTO> findAll(Pageable pageable);

    Optional<OrderShowDTO> findOne(Long id);
   // List<OrderShowDTO> findOrderByUser(Long id);
    void delete(Long id);
}
