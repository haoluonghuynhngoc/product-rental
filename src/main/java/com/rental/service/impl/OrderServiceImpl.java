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
    private NotificationRepository notificationRepository;
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
        orderDTO.setIsRead(false);
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
    //@Transactional(readOnly = true)
    public Optional<OrderShowDTO> findOne(Long id) {
        return orderRepository.findById(id).map(o -> {
            o.setIsRead(true);
            return modelMapper.map(o, OrderShowDTO.class);
        });
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
                    i.setIsRead(true);
                    // đổi thành thông bao information
//                    if (i.getStatus().equals(OrderStatus.DELIVERING)) {
//                        Set<User> user = new HashSet<>();
//                        user.add(i.getUser());
//                        Set<Notification> notifications = notificationRepository.findByUsers(i.getUser()).collect(Collectors.toSet());
//                        notifications.add(Notification.builder()
//                                .title("Xác nhận đơn hàng")
//                                .description("Đơn hàng của bạn đang được giao đến địa chỉ " + i.getAddress())
//                                .isRead(false)
//                                .users(user)
//                                .build());
//                        i.getUser().setNotifications(notifications);
//                    }
                    return modelMapper.map(orderRepository.save(i), OrderShowDTO.class);
                }
        );
    }

    @Override
    public void delete(Long id) {
        // khi người dùng hủy đơn hàng thì thông báo cho người dùng dùng thông báo information

        orderRepository.deleteById(id);
    }

    @Override
    public Integer findALLOrderIsRead() {
        return (int) orderRepository.findAll().stream().filter(
                x -> {
                    if (x.getIsRead() != null)
                        return !x.getIsRead();
                    else
                        return false;
                }
        ).count();
    }

    @Override
    public  List<OrderStatisticsDTO> statisticOrderByYear(int year) {
        List<OrderStatisticsDTO> listOrderStatistics = new ArrayList<>();

        List<Order> listOrderYear = orderRepository.findAll().stream().filter(o ->
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(o.getCreatedDate()));
            return calendar.get(Calendar.YEAR) == year; // đúng năm thì nó lấy k đúng năm thì không lấy
        }).collect(Collectors.toList());

        Map<Integer, List<Order>> monthOrder = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthOrder.put(i, new ArrayList<>());
        }

        listOrderYear.forEach(x -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(x.getCreatedDate()));
            for (int i = 1; i <= 12; i++) {
                if (calendar.get(Calendar.MONTH) + 1 == i) {
                    monthOrder.get(i).add(x);
                }
            }
        });

        monthOrder.forEach((k, v) -> {
            double totalRevenue = 0;
            for (Order order : v) {
                totalRevenue += order.getTotalPrice();
            }
            listOrderStatistics.add(OrderStatisticsDTO.builder()
                    .month(k)
                    .totalOrders(v.size())
                    .totalRevenue(totalRevenue)
                    .build());
        });

        return listOrderStatistics;
    }
}
