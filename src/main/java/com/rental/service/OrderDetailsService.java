package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.OrderDetailsDTO;

/**
 * Service Interface for managing {@link com.swp391.domain.OrderDetails}.
 */
public interface OrderDetailsService {

    OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO);

    OrderDetailsDTO update(OrderDetailsDTO orderDetailsDTO);

    Optional<OrderDetailsDTO> partialUpdate(OrderDetailsDTO orderDetailsDTO);

    Page<OrderDetailsDTO> findAll(Pageable pageable);

    Optional<OrderDetailsDTO> findOne(Long id);

    void delete(Long id);
}
