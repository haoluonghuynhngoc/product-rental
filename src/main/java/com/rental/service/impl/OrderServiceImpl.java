package com.rental.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.rental.domain.*;
import com.rental.domain.enums.OrderStatus;
import com.rental.domain.enums.ProductStatus;
import com.rental.repository.*;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.service.OrderService;

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
    private ProductRepository productRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ModelMapper modelMapper;


    // tối ưu code hơn hàm dưới
    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Set<OrderDetails> orderDetails = new HashSet<>();
        Product product = productRepository.findById(orderDTO.getOrderDetails().getProductId()).get();
        product.setStatus(ProductStatus.RENTING);
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setUser(userRepository.findById(orderDTO.getUserId()).orElse(null));
        order.setStatus(OrderStatus.PENDING);
        order.setTotalQuantity(1);
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
        cartItemsRepository.removeCartItemsByUserAndProduct(
                userRepository.findById(orderDTO.getUserId()).orElse(null), product);
        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
    }

//    @Override
//    public OrderDTO save(OrderDTO orderDTO) {
//        Set<OrderDetails> orderDetails = new HashSet<>();
//        Product product = productRepository.findById(orderDTO.getOrderDetails().getProductId()).get();
//        Order order = Order.builder()
//                .id(-1L)
//                .status(OrderStatus.PENDING)
//                .address(orderDTO.getAddress())
//                .phone(orderDTO.getPhone())
//                .name(orderDTO.getName())
//                .totalQuantity(1)
//                .message(orderDTO.getMessage())
//                .totalPrice(orderDTO.getTotalPrice())
//                .user(userRepository.findById(orderDTO.getUserId()).orElse(null))
//                .build();
//        orderDetails.add(OrderDetails.builder()
//                .deposit(product.getDeposit())
//                .quantity(1)
//                .price(product.getPrice())
//                .product(product)
//                .orderBorrowDate(orderDTO.getOrderDetails().getOrderBorrowDate())
//                .orderReturnDate(orderDTO.getOrderDetails().getOrderReturnDate())
//                .order(order)
//                .build());
//        order.setOrderDetails(orderDetails);
//        if (orderDTO.getVoucherId() != null) {
//            if (voucherRepository.findById(orderDTO.getVoucherId()).orElse(null) != null)
//                order.setVoucher(voucherRepository.findById(orderDTO.getVoucherId()).get());
//        }
//        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
//    }

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
    public List<OrderShowDTO> findOrderByUser(Long id) {
        return orderRepository.findAllByUser(userRepository.findById(id).get()).stream().
                map(i -> modelMapper.map(i, OrderShowDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderShowDTO> update(OrderStatus status, Long id) {
        return orderRepository.findById(id).map(
                i -> {
                    i.setStatus(status);
                    return modelMapper.map(orderRepository.save(i), OrderShowDTO.class);
                }
        );
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
