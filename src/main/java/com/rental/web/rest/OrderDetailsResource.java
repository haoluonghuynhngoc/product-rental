package com.rental.web.rest;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.rental.repository.ProductRepository;
import com.rental.service.dto.NotificationDTO;
import com.rental.service.dto.OrderDetailShowDTO;
import com.rental.service.dto.PagingResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rental.repository.OrderDetailsRepository;
import com.rental.service.OrderDetailsService;
import com.rental.service.dto.OrderDetailsDTO;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsResource {
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private ProductRepository productRepository;

//    @PostMapping("/create")
//    @Operation(deprecated = true)
//    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) {
//        if (orderDetailsDTO.getId() != null) {
//            throw new IllegalArgumentException("A new orderDetails cannot already have an ID ");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(orderDetailsService.save(orderDetailsDTO));
//    }

    @PutMapping("/update")
    @Operation(deprecated = true)
    public ResponseEntity<OrderDetailsDTO> updateOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) {

        if (orderDetailsDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id : id is null");
        }
        if (!orderDetailsRepository.existsById(orderDetailsDTO.getId())) {
            throw new IllegalArgumentException("Cant not find the Order Detail have Id : " + orderDetailsDTO.getId());
        }

        return orderDetailsService.update(orderDetailsDTO).map(
                orderDetailData -> ResponseEntity.status(HttpStatus.OK).body(orderDetailData)
        ).orElseThrow(
                () -> new IllegalArgumentException("Cant not update Order Detail ")
        );
    }


    @GetMapping("/getAll")
    @Operation(deprecated = true)
    public ResponseEntity<PagingResponse<OrderDetailShowDTO>> getAllOrderDetails(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<OrderDetailShowDTO> page = orderDetailsService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("Page in over size ");
        return ResponseEntity.status(HttpStatus.OK).body(
                PagingResponse.<OrderDetailShowDTO>builder()
                        .page(page.getPageable().getPageNumber() + 1)
                        .size(page.getSize())
                        .totalPage(page.getTotalPages())
                        .totalItem(page.getTotalElements())
                        .contends(page.getContent())
                        .build());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<OrderDetailShowDTO> getOrderDetail(@PathVariable Long id) {
        return orderDetailsService.findOne(id).map(
                orderDetailData -> ResponseEntity.status(HttpStatus.OK).body(orderDetailData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not find Order Detail have Id :" + id + " in the data "));
    }

    @DeleteMapping("/remove/{id}")
    @Operation(deprecated = true)
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        if (!orderDetailsRepository.existsById(id))
            throw new IllegalArgumentException("không thể tìm thấy đơn hàng chi tiết có Id :" + id + " trong dữ liệu ");
        orderDetailsService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/getAllByProduct/{id}")
    public ResponseEntity<List<OrderDetailsDTO>> getOrderDetailByProduct(@PathVariable Long id) {
//        if (!productRepository.existsById(id))
//            throw new IllegalArgumentException("Không thể tìm thấy bất kỳ sản phầm nào có id :" + id + " trong dữ liệu");
        List<OrderDetailsDTO> list = orderDetailsService.findAllByProduct(id);
        list.forEach(orderDetailsDTO -> {
            Calendar calendarBorrow = Calendar.getInstance();
            calendarBorrow.setTime(orderDetailsDTO.getOrderBorrowDate());
            calendarBorrow.add(Calendar.DATE, -1);
            orderDetailsDTO.setOrderBorrowDate(calendarBorrow.getTime());

            Calendar calendarReturn = Calendar.getInstance();
            calendarReturn.setTime(orderDetailsDTO.getOrderReturnDate());
            calendarReturn.add(Calendar.DATE, 1);
            orderDetailsDTO.setOrderReturnDate(calendarReturn.getTime());
        });
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
