package com.rental.service;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rental.service.dto.OrderDetailsDTO;
public interface OrderDetailsService {

    OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO);

    Optional<OrderDetailsDTO> update(OrderDetailsDTO orderDetailsDTO);

    Page<OrderDetailsDTO> findAll(Pageable pageable);

    Optional<OrderDetailsDTO> findOne(Long id);

    void delete(Long id);
}
