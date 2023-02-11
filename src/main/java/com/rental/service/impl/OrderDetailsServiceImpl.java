package com.rental.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.domain.OrderDetails;
import com.rental.repository.OrderDetailsRepository;
import com.rental.service.OrderDetailsService;
import com.rental.service.dto.OrderDetailsDTO;

/**
 * Service Implementation for managing {@link OrderDetails}.
 */
@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository) {
        this.orderDetailsRepository = orderDetailsRepository;

    }

    @Override
    public OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO) {

        OrderDetails orderDetails = modelMapper.map(orderDetailsDTO, OrderDetails.class);
        orderDetails = orderDetailsRepository.save(orderDetails);
        return modelMapper.map(orderDetails, OrderDetailsDTO.class);
    }

    @Override
    public OrderDetailsDTO update(OrderDetailsDTO orderDetailsDTO) {

        OrderDetails orderDetails = modelMapper.map(orderDetailsDTO, OrderDetails.class);
        orderDetails = orderDetailsRepository.save(orderDetails);
        return modelMapper.map(orderDetails, OrderDetailsDTO.class);
    }

    @Override
    public Optional<OrderDetailsDTO> partialUpdate(OrderDetailsDTO orderDetailsDTO) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDetailsDTO> findAll(Pageable pageable) {

        return orderDetailsRepository.findAll(pageable).map(o -> {
            return modelMapper.map(o, OrderDetailsDTO.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailsDTO> findOne(Long id) {

        return orderDetailsRepository.findById(id).map(o -> {
            return modelMapper.map(o, OrderDetailsDTO.class);
        });
    }

    @Override
    public void delete(Long id) {

        orderDetailsRepository.deleteById(id);
    }
}
