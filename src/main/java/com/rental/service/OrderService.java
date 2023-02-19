package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.OrderDTO;

public interface OrderService {

    OrderDTO save(OrderDTO orderDTO);

    Optional<OrderDTO> update(OrderDTO orderDTO);

    Page<OrderDTO> findAll(Pageable pageable);

    Optional<OrderDTO> findOne(Long id);

    void delete(Long id);
}
