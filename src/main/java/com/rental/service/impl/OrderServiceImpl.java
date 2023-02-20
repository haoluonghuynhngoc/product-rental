package com.rental.service.impl;

import java.util.Optional;

import com.rental.domain.User;
import com.rental.domain.Voucher;
import com.rental.repository.UserRepository;
import com.rental.repository.VoucherRepository;
import com.rental.service.dto.UserDTO;
import com.rental.service.dto.VoucherDTO;
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

    //    private final OrderRepository orderRepository;
//    public OrderServiceImpl(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//
//    }
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        order = orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    @Transactional
    public Optional<OrderDTO> update(OrderDTO orderDTO) {
        orderDTO.setUser(modelMapper.map(userRepository.findById(orderDTO.getUser().getId()).orElse(null), UserDTO.class));
        orderDTO.setVoucher(modelMapper.map(voucherRepository.findById(orderDTO.getVoucher().getId()).orElse(null), VoucherDTO.class));
        return orderRepository.findById(orderDTO.getId()).map(
                existingOrder -> {
//     nếu voucher và user không trả về giá trị thì nó sẽ mặt định lấy giá trị cũ
                    if (orderDTO.getVoucher() == null)
                        orderDTO.setVoucher(modelMapper.map(existingOrder.getVoucher(), VoucherDTO.class));
                    if (orderDTO.getUser() == null)
                        orderDTO.setUser(modelMapper.map(existingOrder.getUser(), UserDTO.class));
//
                    existingOrder.setVoucher(voucherRepository.findById(orderDTO.getVoucher().getId()).orElse(null));
                    existingOrder.setUser(userRepository.findById(orderDTO.getUser().getId()).orElse(null));
                    modelMapper.map(orderDTO, existingOrder);
                    return existingOrder;
                }
        ).map(orderRepository::save).map(
                o -> {
                    return modelMapper.map(o, OrderDTO.class);
                }
        );
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
