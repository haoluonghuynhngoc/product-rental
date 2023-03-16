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
    private InformationRepository informationRepository;
    @Autowired
    private ModelMapper modelMapper;


    //    @Override
//    public OrderDTO save(OrderDTO orderDTO) {
//        Set<OrderDetails> orderDetails = new HashSet<>();
//        Product product = productRepository.findById(orderDTO.getOrderDetails().getProductId()).get();
//        product.setStatus(ProductStatus.RENTING);
//        Order order = modelMapper.map(orderDTO, Order.class);
//        order.setUser(userRepository.findById(orderDTO.getUserId()).orElse(null));
//        order.setStatus(OrderStatus.PENDING);
//        order.setTotalQuantity(1);
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
//        cartItemsRepository.removeCartItemsByUserAndProduct(
//                userRepository.findById(orderDTO.getUserId()).orElse(null), product);
//        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
//    }
// =======================================================================
    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Set<OrderDetails> orderDetails = new HashSet<>();
        Product product = productRepository.findById(orderDTO.getOrderDetails().getProductId()).get();
        product.setStatus(ProductStatus.RENTING);
        User userData = userRepository.findById(orderDTO.getUserId()).get();
        Order orderConvert = modelMapper.map(orderDTO, Order.class);
        orderConvert.setUser(userData);
        orderConvert.setStatus(OrderStatus.PENDING);
        orderConvert.setTotalQuantity(1);
        orderDetails.add(OrderDetails.builder()
                .deposit(product.getDeposit())
                .quantity(1)
                .price(product.getPrice())
                .product(product)
                .orderBorrowDate(orderDTO.getOrderDetails().getOrderBorrowDate())
                .orderReturnDate(orderDTO.getOrderDetails().getOrderReturnDate())
                .order(orderConvert)
                .build());
        orderConvert.setOrderDetails(orderDetails);
        if (orderDTO.getVoucherId() != null) {
            if (voucherRepository.findById(orderDTO.getVoucherId()).orElse(null) != null)
                orderConvert.setVoucher(voucherRepository.findById(orderDTO.getVoucherId()).get());
        }
        cartItemsRepository.removeCartItemsByUserAndProduct(
                userRepository.findById(orderDTO.getUserId()).orElse(null), product);
        Order order = orderRepository.save(orderConvert); // save to database
        informationRepository.save(Information.builder()
                .order(order)
                .title("Đơn Hàng chờ kiểm duyệt")
                .user(userData)
                .isRead(false)
                .description("Đã có đơn hàng từ sản phẩm " + product.getName() + " từ người dùng " + userData.getUsername() + " vui lòng kiểm tra thông tin")
                .build());
        return modelMapper.map(order, OrderDTO.class);
    }

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
                    // sửa chổ này
                    i.setStatus(status);
                    if (i.getStatus().equals(OrderStatus.DELIVERING)) {
                        Set<User> user = new HashSet<>();// tạo 2 set để set thông báo cho user
                        Set<Notification> notifications = new HashSet<>();
                        user.add(i.getUser());
                        notifications.add(Notification.builder()
                                .title("Xác nhận đơn hàng")
                                .description("Đơn hàng của bạn đang được giao đến địa chỉ "+i.getAddress())
                                .users(user)
                                .isRead(false)
                                .build());
                        i.getUser().setNotifications(notifications);
                    }
                    return modelMapper.map(orderRepository.save(i), OrderShowDTO.class);
                }
        );
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
