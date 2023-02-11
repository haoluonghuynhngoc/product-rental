package com.rental.web.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rental.repository.OrderDetailsRepository;
import com.rental.service.OrderDetailsService;
import com.rental.service.dto.OrderDetailsDTO;

/**
 * REST controller for managing {@link com.swp391.domain.OrderDetails}.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailsResource {

    private final OrderDetailsService orderDetailsService;

    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsResource(OrderDetailsService orderDetailsService,
            OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsService = orderDetailsService;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    @PostMapping("/order-details")
    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        if (orderDetailsDTO.getId() != null) {
            throw new IllegalArgumentException("A new orderDetails cannot already have an ID : idexists");
        }
        OrderDetailsDTO result = orderDetailsService.save(orderDetailsDTO);
        return ResponseEntity.ok().body(result);

    }

    @PutMapping("/order-details/{id}")
    public ResponseEntity<OrderDetailsDTO> updateOrderDetails(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDetailsDTO orderDetailsDTO) {

        if (orderDetailsDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id : idnull");
        }
        if (!Objects.equals(id, orderDetailsDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID : idinvalid");
        }

        if (!orderDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found : idnotfound");
        }

        OrderDetailsDTO result = orderDetailsService.update(orderDetailsDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/order-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDetailsDTO> partialUpdateOrderDetails(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDetailsDTO orderDetailsDTO) {

        if (orderDetailsDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id : idnull");
        }
        if (!Objects.equals(id, orderDetailsDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID : idinvalid");
        }

        if (!orderDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found : idnotfound");
        }

        Optional<OrderDetailsDTO> result = orderDetailsService.partialUpdate(orderDetailsDTO);

        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/order-details")
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrderDetails(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {

        Page<OrderDetailsDTO> page = orderDetailsService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/order-details/{id}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable Long id) {

        Optional<OrderDetailsDTO> orderDetailsDTO = orderDetailsService.findOne(id);
        return orderDetailsDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/order-details/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {

        orderDetailsService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
