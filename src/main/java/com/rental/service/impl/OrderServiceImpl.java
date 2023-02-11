package com.rental.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.domain.Order;
import com.rental.repository.OrderRepository;
import com.rental.service.OrderService;
import com.rental.service.dto.OrderDTO;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;

    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {

        Order order = modelMapper.map(orderDTO, Order.class);
        order = orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {

        Order order = modelMapper.map(orderDTO, Order.class);
        order = orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {

        return orderRepository.findAll(pageable).map(o -> {
            return modelMapper.map(o, OrderDTO.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {

        return orderRepository.findById(id).map(o -> {
            return modelMapper.map(o, OrderDTO.class);
        });
    }

    @Override
    public void delete(Long id) {

        orderRepository.deleteById(id);
    }
}
