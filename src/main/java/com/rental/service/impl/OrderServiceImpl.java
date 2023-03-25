package com.rental.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.rental.domain.*;
import com.rental.domain.enums.InformationStatus;
import com.rental.domain.enums.OrderStatus;
import com.rental.domain.enums.ProductStatus;
import com.rental.domain.enums.UserStatus;
import com.rental.repository.*;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private InformationRepository informationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


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
        return modelMapper.map(orderRepository.save(orderConvert), OrderDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderShowDTO> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(o -> {
            return modelMapper.map(o, OrderShowDTO.class);
        });
    }

    @Override
    public PagingResponse<OrderShowDTO> findAllHistory(Pageable pageable,OrderStatus status) {
        List<OrderShowDTO> listStatus = orderRepository.findAllByStatus(status).map(
                o -> modelMapper.map(o, OrderShowDTO.class)
        ).collect(Collectors.toList());
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), listStatus.size());
        Page<OrderShowDTO> pageProduct = new PageImpl<>(listStatus.subList(start, end), pageable, listStatus.size());
        return PagingResponse.<OrderShowDTO>builder()
                .page(pageProduct.getPageable().getPageNumber() + 1)
                .size(pageProduct.getSize())
                .totalPage(pageProduct.getTotalPages())
                .totalItem(pageProduct.getTotalElements())
                .contends(pageProduct.getContent())
                .build();
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
    public Optional<OrderShowDTO> update(OrderStatus status, Long id, String contend) {
        return orderRepository.findById(id).map(
                i -> {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String url = "";
                    List<Image> images = new ArrayList<>();
                    i.getOrderDetails().forEach(
                            orderDetails -> {
                                if (orderDetails.getProduct().getImages() != null)
                                    images.addAll(orderDetails.getProduct().getImages());
                            }
                    );
                    if (!images.isEmpty()) { // cái này để check rằng nếu sản phẩm không có ảnh thì cũng không thể quăn lỗi
                        url = images.get(0).getUrl();
                    }
                    if (status.equals(OrderStatus.DELIVERING)) {
                        i.getOrderDetails().forEach(
                                orderDetails -> {
                                    Calendar currentDate = Calendar.getInstance();
                                    currentDate.add(Calendar.DATE, 2);
                                    Calendar currentDateCheck = Calendar.getInstance();
                                    Calendar borrowDay = Calendar.getInstance();
                                    borrowDay.setTime(orderDetails.getOrderBorrowDate());
                                    if (currentDateCheck.getTime().compareTo(borrowDay.getTime()) > 0) { // không cho ngày quá khứ
                                        throw new IllegalArgumentException("Sản Phẩm " + orderDetails.getProduct().getName() + " có ngày giao là "
                                                + formatter.format(borrowDay.getTime())
                                                + " nên đã qua ngày giao hàng ");
                                    }
                                    if (currentDate.getTime().compareTo(borrowDay.getTime()) < 0) { // set ngày hiện tại lên 2 ngày
                                        throw new IllegalArgumentException("Sản Phẩm " + orderDetails.getProduct().getName() + " có ngày giao là "
                                                + formatter.format(borrowDay.getTime()) + " nên giao lúc này là không hợp lệ, " +
                                                "chỉ giao những đơn hàng có ngày thuê lớn hơn ngày hiện tại từ 1 đến 2 ngày ");
                                    }
                                }
                        );
                        informationRepository.save(Information.builder()
                                .order(i)
                                .status(InformationStatus.CUSTOMER)
                                .title("Đơn Hàng Của Bạn Đã Được Giao")
                                .user(i.getUser())
                                .isRead(false)
                                .image(url)
                                .description("Đơn hàng của bạn đang được giao đến địa chỉ " + i.getAddress() +
                                        " vui lòng kiểm tra thông tin")
                                .build());
                        i.getOrderDetails().forEach(
                                p -> p.getProduct().setStatus(ProductStatus.RENTING));
                    } else if (status.equals(OrderStatus.CANCELLED)) {
                        informationRepository.save(Information.builder()
                                .order(i)
                                .status(InformationStatus.CUSTOMER)
                                .title("Đơn Hàng Của Bạn Đã Đã Bị Hủy")
                                .user(i.getUser())
                                .image(url)
                                .isRead(false)
                                .description(contend)
                                .build());
                        i.getOrderDetails().forEach(
                                p -> p.getProduct().setStatus(ProductStatus.APPROVED)
                        );
                    } else if (status.equals(OrderStatus.PAID)) {
                        informationRepository.save(Information.builder()
                                .order(i)
                                .status(InformationStatus.CUSTOMER)
                                .image(url)
                                .title("Xin Cảm Ơn")
                                .user(i.getUser())
                                .isRead(false)
                                .description("Cảm ơn bạn đã tin tưởng và sử dụng sản phẩm của chúng tôi " +
                                        " admid sẽ liên hệ với bạn sớm nhất để hoàn trả tiền cọc")
                                .build());
                        i.getOrderDetails().forEach(
                                p -> p.getProduct().setStatus(ProductStatus.APPROVED)
                        );
                    }else if (status.equals(OrderStatus.CONFIRMED)) {
                        informationRepository.save(Information.builder()
                                .order(i)
                                .status(InformationStatus.CUSTOMER)
                                .image(url)
                                .title("Đơn Hàng Của Bạn Đã Được Chấp Nhận")
                                .user(i.getUser())
                                .isRead(false)
                                .description("Admin đã chấp nhận đợn hàng của bạn chúng tôi sẽ giao sản phẩm đến cho bạn trước 1 ngày")
                                .build());
                        i.getOrderDetails().forEach(
                                p -> p.getProduct().setStatus(ProductStatus.APPROVED)
                        );
                    }
                    i.setStatus(status);
                    i.setIsRead(true);
                    return modelMapper.map(orderRepository.save(i), OrderShowDTO.class);
                }
        );
    }

    @Override
    public void delete(Long id) {
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
    public List<OrderStatisticsDTO> statisticOrderByYear(int year) {
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
