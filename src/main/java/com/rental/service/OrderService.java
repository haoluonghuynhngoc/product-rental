package com.rental.service;

import java.util.Optional;

import com.rental.service.dto.OrderShowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.OrderDTO;

public interface OrderService {

    OrderDTO save(OrderDTO orderDTO);

    Optional<OrderDTO> update(OrderDTO orderDTO);

    Page<OrderShowDTO> findAll(Pageable pageable);

    Optional<OrderShowDTO> findOne(Long id);

    void delete(Long id);
}
