package com.rental.service.impl;

import java.util.Optional;

import com.rental.repository.OrderRepository;
import com.rental.repository.ProductRepository;
import com.rental.service.dto.OrderDTO;
import com.rental.service.dto.ProductDTO;
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
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO) {
        OrderDetails orderDetails = modelMapper.map(orderDetailsDTO, OrderDetails.class);
        orderDetails = orderDetailsRepository.save(orderDetails);
        return modelMapper.map(orderDetails, OrderDetailsDTO.class);
    }

    @Override
    public Optional<OrderDetailsDTO> update(OrderDetailsDTO orderDetailsDTO) {
        orderDetailsDTO.setOrder(modelMapper.map(orderRepository.findById(orderDetailsDTO.getOrder().getId()), OrderDTO.class));
        orderDetailsDTO.setProduct(modelMapper.map(productRepository.findById(orderDetailsDTO.getProduct().getId()), ProductDTO.class));
        return orderDetailsRepository.findById(orderDetailsDTO.getId()).map(
                orderDetailEntity -> {
                    if (orderDetailsDTO.getOrder() == null)
                        orderDetailsDTO.setOrder(modelMapper.map(orderDetailEntity.getOrder(), OrderDTO.class));
                    if (orderDetailsDTO.getProduct() == null)
                        orderDetailsDTO.setProduct(modelMapper.map(orderDetailEntity.getProduct(), ProductDTO.class));
                    orderDetailEntity.setOrder(orderRepository.findById(orderDetailsDTO.getOrder().getId()).orElseThrow(null));
                    orderDetailEntity.setProduct(productRepository.findById(orderDetailsDTO.getProduct().getId()).orElseThrow(null));
                    modelMapper.map(orderDetailsDTO, orderDetailEntity);
                    return orderDetailEntity;
                }
        ).map(orderDetailsRepository::save).map(
                b -> {
                    return modelMapper.map(b, OrderDetailsDTO.class);
                }
        );
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
