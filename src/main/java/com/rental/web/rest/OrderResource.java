package com.rental.web.rest;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.rental.domain.Order;
import com.rental.domain.OrderDetails;
import com.rental.domain.Product;
import com.rental.domain.enums.OrderStatus;
import com.rental.domain.enums.ProductStatus;
import com.rental.repository.OrderDetailsRepository;
import com.rental.repository.ProductRepository;
import com.rental.repository.UserRepository;
import com.rental.service.dto.OrderShowDTO;
import com.rental.service.dto.OrderStatisticsDTO;
import com.rental.service.dto.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rental.repository.OrderRepository;
import com.rental.service.OrderService;
import com.rental.service.dto.OrderDTO;

@RestController
@RequestMapping("/api/order")
public class OrderResource {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
//        if (productRepository.findById(orderDTO.getOrderDetails().getProductId()).get().getStatus().equals(ProductStatus.RENTING))
//            throw new IllegalArgumentException("Đồ của bạn vừa được người dùng khác thuê ");
        if (!userRepository.existsById(orderDTO.getUserId()))
            throw new IllegalArgumentException("Không thể tìm thấy người dùng có Id : " + orderDTO.getUserId() + " trong dữ liệu ");
        if (!productRepository.existsById(orderDTO.getOrderDetails().getProductId())) {
            throw new IllegalArgumentException("Không thể tìm thấy sản phẩm có Id : " + orderDTO.getOrderDetails().getProductId() + " trong dữ liệu");
        }
        List<OrderDetails> listOrderDetails = orderDetailsRepository.findAllByProduct(productRepository.findById(
                orderDTO.getOrderDetails().getProductId()).orElse(null));
        if (listOrderDetails != null) {
            listOrderDetails.forEach(orderDetails -> {
                Calendar borrowDateClient = Calendar.getInstance();
                borrowDateClient.setTime(orderDTO.getOrderDetails().getOrderBorrowDate());
                Calendar returnDateClient = Calendar.getInstance();
                returnDateClient.setTime(orderDTO.getOrderDetails().getOrderReturnDate());
                Calendar startDateDataBase = Calendar.getInstance();
                startDateDataBase.setTime(orderDetails.getOrderBorrowDate());
                Calendar endDateDataBase = Calendar.getInstance();
                endDateDataBase.setTime(orderDetails.getOrderReturnDate());
                if (borrowDateClient.compareTo(returnDateClient) > 0) {
                    throw new IllegalArgumentException("Ngày đặt hàng lớn hơn ngày trả hàng xin thử lại ");
                }
                endDateDataBase.add(Calendar.DATE, 2);// thêm vào để check điều kiện (đã kiểm tra)
                Calendar currentDate = borrowDateClient;
                startDateDataBase.add(Calendar.DATE, -1);// chưa kiểm tra kỹ
                while (currentDate.before(returnDateClient) || currentDate.equals(returnDateClient)) {
                    if (currentDate.after(startDateDataBase) && currentDate.before(endDateDataBase)) {
                        startDateDataBase.add(Calendar.DATE, 2); // thêm 2 ngày để check điều kiện
                        if (borrowDateClient.equals(startDateDataBase)) {
                            throw new IllegalArgumentException("Xin lỗi chúng tôi cần 1 ngày để bảo dưỡng sản phầm," +
                                    " xin vui lòng chọn ngày đặt hơn 1 ngày ");
                        } else {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            startDateDataBase.add(Calendar.DATE, -2);// thêm 1 ngày để trả đúng số ngày
                            startDateDataBase.add(Calendar.DATE, 1); // cộng 1 ngày lên để in ra đúng
                            endDateDataBase.add(Calendar.DATE, -2);//  trừ  để in ra thông báo
                            throw new IllegalArgumentException(
                                    "Ngày thuê " + formatter.format(currentDate.getTime()) + " của bạn đã bị trùng với ngày thuê của người dùng khác " +
                                            formatter.format(startDateDataBase.getTime()) + " đến " + formatter.format(endDateDataBase.getTime()));
                        }
                    }
                    currentDate.add(Calendar.DATE, 1);
                }
            });
        }

        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(orderDTO));
    }


    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<OrderShowDTO> update(@PathVariable(name = "id") Long id, @RequestParam OrderStatus status) {
        if (!orderRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thây id : " + id + " trong dữ liệu");
        return orderService.update(status, id).map(
                orderData -> ResponseEntity.status(HttpStatus.OK).body(orderData)
        ).orElseThrow(
                () -> new IllegalArgumentException("Cant not update order")
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<PagingResponse<OrderShowDTO>> getAllOrders(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<OrderShowDTO> page = orderService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                PagingResponse.<OrderShowDTO>builder()
                        .page(page.getPageable().getPageNumber() + 1)
                        .size(page.getSize())
                        .totalPage(page.getTotalPages())
                        .totalItem(page.getTotalElements())
                        .contends(page.getContent())
                        .build()
        );
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<OrderShowDTO> getOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy đơn hàng có Id :" + id + "trong dư liệu ");
        return orderService.findOne(id).map(
                orderData -> ResponseEntity.status(HttpStatus.OK).body(orderData)
        ).orElseThrow(
                () -> new IllegalArgumentException("Không thể tìm thấy đơn hàng")
        );
    }

    @GetMapping("/getAllByUser/{id}")
    public ResponseEntity<List<OrderShowDTO>> getAllOrderByUser(@PathVariable Long id) {
        if (!userRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm người dùng có Id :" + id + "trong dữ liệu ");
        List<OrderShowDTO> list = orderService.findOrderByUser(id);
        list.forEach(x ->
        {
            x.setUser(null);
        });
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!orderRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy đơn hàng có id là : " + id);
        orderService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/getCountIsRead")
    public ResponseEntity<Integer> getCountRead() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findALLOrderIsRead());
    }

    @GetMapping("/getStatic/{year}")
    public ResponseEntity<?> getOrderStatistics(@PathVariable(name = "year") int year) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.statisticOrderByYear(year));
    }
}
