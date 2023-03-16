package com.rental.service;
import java.util.List;
import java.util.Optional;

import com.rental.service.dto.OrderDetailShowDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rental.service.dto.OrderDetailsDTO;
public interface OrderDetailsService {

//    OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO);

    Optional<OrderDetailsDTO> update(OrderDetailsDTO orderDetailsDTO);

    Page<OrderDetailShowDTO> findAll(Pageable pageable);

    Optional<OrderDetailShowDTO> findOne(Long id);
    List<OrderDetailsDTO> findAllByProduct(Long id);
    void delete(Long id);
}
