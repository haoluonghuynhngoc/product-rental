package com.rental.web.rest;

import com.rental.domain.Image;
import com.rental.domain.Information;
import com.rental.domain.Order;
import com.rental.domain.Product;
import com.rental.domain.enums.InformationStatus;
import com.rental.domain.enums.OrderStatus;
import com.rental.repository.*;
import com.rental.service.EmailSenderService;
import com.rental.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AutomaticSchedule {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InformationRepository informationRepository;

    //@Scheduled(fixedDelay = 100000) // (100000 / 60000) =  1.6 phut
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduleFixedDelayTask() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<Order> list = orderRepository.findAll().stream().filter(
                order -> {
                    return !order.getStatus().equals(OrderStatus.CANCELLED);
                }).collect(Collectors.toList());
        if (!list.isEmpty()) {
            list.forEach(
                    order -> {
                        orderDetailsRepository.findAllByOrder(order).forEach(
                                orderDetails -> {
                                    Calendar currentDate = Calendar.getInstance();
                                    currentDate.add(Calendar.DATE, 1);
                                    Calendar returnDate = Calendar.getInstance();
                                    returnDate.setTime(orderDetails.getOrderReturnDate());
                                    Calendar borrowDay = Calendar.getInstance();
                                    borrowDay.setTime(orderDetails.getOrderBorrowDate());
                                    if (formatter.format(currentDate.getTime()).equals(
                                            formatter.format(returnDate.getTime()))) {
                                        if (order.getUser().getEmail() != null) {
                                            System.out.println(order.getId());
                                            System.out.println(order.getUser().getEmail());

                                            emailSenderService.sendSimpleEmail(
                                                    order.getUser().getEmail(),
//                                                    "Đơn hàng  của bạn đặt từ ngày " + formatter.format(borrowDay.getTime()) + " đến ngày "
//                                                            + formatter.format(returnDate.getTime()) + " xắp đến ngày trả hàng "
//                                                            + "vui lòng liện hệ với admin để trả hàng và nhận lại tiền cọc ",
                                                    " Thân gửi bạn " + order.getUser().getLastName() +" "+ order.getUser().getFirstName() + ",\n" +
                                                            "Web Thuê Đồ xin thông báo đến bạn có một đơn hàng:\n" +
                                                            ". Mã: " + orderDetails.getProduct().getId() + "\n" +
                                                            ". Sản phẩm: " + orderDetails.getProduct().getName() + "\n" +
                                                            ". Thời gian thuê: ngày " + formatter.format(borrowDay.getTime()) + " đến ngày " + formatter.format(returnDate.getTime()) + ",\n" +
                                                            "Đơn hàng của bạn sắp đến hạn trả hàng. \n" +
                                                            "Mong bạn vui lòng sắp xếp thời gian liên hệ với Thuê Đồ để gửi trả lại hàng cho bên mình đúng hạn và nhận lại tiền đặt cọc sản phẩm!\n" +
                                                            "Cảm ơn bạn đã sử dụng dịch vụ thuê đồ của chúng mình! Mong bạn đã có trải nghiệm tốt nhất!\n" +
                                                            "Thuê Đồ\n" +
                                                            "\n" +
                                                            "Thông tin liên hệ:\n" +
                                                            ". Email: haoluonghuynh2001@gmail.com\n" +
                                                            ". Phone: 0912321232\n",

                                                    " Thông báo ngày trả sản phẩm " + orderDetails.getProduct().getName()
                                            );
                                        }
                                    }
                                });
                    });
        }
    }

    //  @Scheduled(fixedDelay = 100000)
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduleFixedDelayAdmin() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        List<Order> list = orderRepository.findAll().stream().filter(
                order -> {
                    return !order.getStatus().equals(OrderStatus.CANCELLED);
                }).collect(Collectors.toList());
        if (!list.isEmpty()) {
            list.forEach(
                    order -> {
                        orderDetailsRepository.findAllByOrder(order).forEach(
                                orderDetails -> {
                                    String imageUrl = "";
                                    Calendar currentDate = Calendar.getInstance();
                                    currentDate.add(Calendar.DATE, 1);
                                    Calendar returnDate = Calendar.getInstance();
                                    returnDate.setTime(orderDetails.getOrderReturnDate());
                                    Calendar borrowDay = Calendar.getInstance();
                                    borrowDay.setTime(orderDetails.getOrderBorrowDate());
                                    if (formatter.format(currentDate.getTime()).equals(
                                            formatter.format(returnDate.getTime()))) {
                                        System.out.println("Sending info ... order " + order.getId()
                                                + " and product " + orderDetails.getProduct().getId() + " to admin");
                                        Product product = productRepository.findById(orderDetails.getProduct().getId()).orElse(null);

//                                       if (product!=null) {
//                                           List<Image> imageList = new ArrayList<>();
//                                           if (!product.getImages().isEmpty()){
//                                               imageList.addAll(product.getImages());
//                                           }
//                                           if (imageList.isEmpty()){
//                                               imageUrl=imageList.get(0).getUrl();
//                                           }
//                                       }
                                        informationRepository.save(
                                                Information.builder()
                                                        .title("Sản Phẩm Xắp Đến Ngày Trả Hàng")
                                                        .description("Sản phẩm " + orderDetails.getProduct().getName() + " còn 1 ngày nữa đã " +
                                                                " hết ngày mượn hàng của user ")
                                                        .image(imageUrl)
                                                        .isRead(false)
                                                        .status(InformationStatus.CENSORSHIP)
                                                        .user(userRepository.findById(1L).orElse(null))
                                                        .order(order)
                                                        .build());
                                    }
                                });
                    });
        }
    }
}
