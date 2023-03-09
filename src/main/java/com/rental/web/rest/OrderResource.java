package com.rental.web.rest;

import java.util.List;
import com.rental.domain.enums.OrderStatus;
import com.rental.domain.enums.ProductStatus;
import com.rental.repository.ProductRepository;
import com.rental.repository.UserRepository;
import com.rental.service.dto.OrderShowDTO;
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

    @PostMapping("/create")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO orderDTO) {
        if (productRepository.findById(orderDTO.getOrderDetails().getProductId()).get().getStatus().equals(ProductStatus.RENTING))
            throw new IllegalArgumentException("Đồ của bạn vừa được người dùng khác thuê ");
        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(orderDTO));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<OrderShowDTO> update(@PathVariable(name = "id") Long id, @RequestParam OrderStatus status) {
        if (!orderRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thây id : " + id +" trong dữ liệu");
        return orderService.update(status,id).map(
                orderData -> ResponseEntity.status(HttpStatus.OK).body(orderData)
        ).orElseThrow(
                () -> new IllegalArgumentException("Cant not update order")
        );
    }


    //    @GetMapping("/getAll")
//    public ResponseEntity<?> getAllOrders(
//            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
//        Page<OrderShowDTO> page = orderService.findAll(pageable);
//        if (page.isEmpty())
//            throw new IllegalArgumentException("list is over size order");
//        return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
//    }
    @GetMapping("/getAll")
    public ResponseEntity<PagingResponse<OrderShowDTO>> getAllOrders(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<OrderShowDTO> page = orderService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("list is over size order");
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
        List<OrderShowDTO> list =orderService.findOrderByUser(id);
        list.forEach(x->
        {
            x.getOrderDetails().forEach(orderDetail->orderDetail.setProduct(null));
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

}
