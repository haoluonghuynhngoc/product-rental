package com.rental.web.rest;

import com.rental.repository.CartItemsRepository;
import com.rental.repository.UserRepository;
import com.rental.service.CartItemsService;
import com.rental.service.dto.CartItemsDTO;
import com.rental.service.dto.CartItemsShowDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-iteam")
public class CartItemsResource {
    @Autowired
    private CartItemsService cartItemsService;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/create")
    public ResponseEntity<CartItemsDTO> createCategory(@RequestBody CartItemsDTO cartItemsDTO) {
        if (cartItemsDTO.getId() != null)
            throw new IllegalArgumentException("Thêm vào giỏ hàng không cần ID");
        return ResponseEntity.status(HttpStatus.OK).body(cartItemsService.saveCart(cartItemsDTO));
    }

    @GetMapping("/viewCart/{id}") // tìm theo id người dùng
    public ResponseEntity<CartItemsShowDTO> getCartByUser(@PathVariable Long id) {
        if (!userRepository.existsById(id))
            throw new IllegalArgumentException("Người dùng không tồn tại");
        return ResponseEntity.status(HttpStatus.OK).body(cartItemsService.findAll(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        if (!cartItemsRepository.existsById(id))
            throw new IllegalArgumentException("sản phầm trong giỏ hàng của người dùng không có");
        cartItemsService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
