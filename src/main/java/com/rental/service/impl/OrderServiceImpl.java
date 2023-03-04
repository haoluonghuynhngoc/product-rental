package com.rental.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.rental.domain.*;
import com.rental.domain.enums.OrderStatus;
import com.rental.repository.*;
import com.rental.service.dto.OrderShowDTO;
import com.rental.service.dto.UserDTO;
import com.rental.service.dto.VoucherDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.service.OrderService;
import com.rental.service.dto.OrderDTO;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Override
    public Optional<OrderDTO> update(OrderDTO orderDTO) {
        return Optional.empty();
    }


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
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Set<OrderDetails> orderDetails = new HashSet<>();
        Product product = productRepository.findById(orderDTO.getOrderDetails().getProductId()).get();
        Order order = Order.builder()
                .id(-1L)
                .status(OrderStatus.PENDING)
                .totalQuantity(1)
                .totalPrice(orderDTO.getTotalPrice())
                .user(userRepository.findById(orderDTO.getUserId()).orElse(null))
                .build();
        orderDetails.add(OrderDetails.builder()
                .deposit(product.getDeposit())
                .quantity(1)
                .price(product.getPrice())
                .product(product)
                .orderBorrowDate(orderDTO.getOrderDetails().getOrderBorrowDate())
                .orderReturnDate(orderDTO.getOrderDetails().getOrderReturnDate())
                .order(order)
                .build());
        order.setOrderDetails(orderDetails);
        if (orderDTO.getVoucherId() != null) {
            if (voucherRepository.findById(orderDTO.getVoucherId()).orElse(null) != null)
                order.setVoucher(voucherRepository.findById(orderDTO.getVoucherId()).get());
        }
        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
    }

    //    @Override
//    @Transactional
//    public Optional<OrderDTO> update(OrderDTO orderDTO) {
//        orderDTO.setUser(modelMapper.map(userRepository.findById(orderDTO.getUser().getId()).orElse(null), UserDTO.class));
//        orderDTO.setVoucher(modelMapper.map(voucherRepository.findById(orderDTO.getVoucher().getId()).orElse(null), VoucherDTO.class));
//        return orderRepository.findById(orderDTO.getId()).map(
//                existingOrder -> {
////     nếu voucher và user không trả về giá trị thì nó sẽ mặt định lấy giá trị cũ
//                    if (orderDTO.getVoucher() == null)
//                        orderDTO.setVoucher(modelMapper.map(existingOrder.getVoucher(), VoucherDTO.class));
//                    if (orderDTO.getUser() == null)
//                        orderDTO.setUser(modelMapper.map(existingOrder.getUser(), UserDTO.class));
////
//                    existingOrder.setVoucher(voucherRepository.findById(orderDTO.getVoucher().getId()).orElse(null));
//                    existingOrder.setUser(userRepository.findById(orderDTO.getUser().getId()).orElse(null));
//                    modelMapper.map(orderDTO, existingOrder);
//                    return existingOrder;
//                }
//        ).map(orderRepository::save).map(
//                o -> {
//                    return modelMapper.map(o, OrderDTO.class);
//                }
//        );
//    }
//
    @Override
    @Transactional(readOnly = true)
    public Page<OrderShowDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(o -> {
            return modelMapper.map(o, OrderShowDTO.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderShowDTO> findOne(Long id) {
        return orderRepository.findById(id).map(o ->
                modelMapper.map(o, OrderShowDTO.class)
        );
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
