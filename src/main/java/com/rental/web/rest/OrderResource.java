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

import com.rental.repository.OrderRepository;
import com.rental.service.OrderService;
import com.rental.service.dto.OrderDTO;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private final OrderService orderService;

    private final OrderRepository orderRepository;

    public OrderResource(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {

        if (orderDTO.getId() != null) {
            throw new IllegalArgumentException("A new order cannot already have an ID  : idexists");
        }
        OrderDTO result = orderService.save(orderDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDTO orderDTO) {

        if (orderDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        OrderDTO result = orderService.update(orderDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDTO> partialUpdateOrder(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody OrderDTO orderDTO) {

        if (orderDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, orderDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        Optional<OrderDTO> result = orderService.partialUpdate(orderDTO);

        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {

        Page<OrderDTO> page = orderService.findAll(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {

        Optional<OrderDTO> orderDTO = orderService.findOne(id);
        return orderDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {

        orderService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
