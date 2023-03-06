package com.rental.web.rest;

import com.rental.repository.CartItemsRepository;
import com.rental.repository.ProductRepository;
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
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/create")
    public ResponseEntity<CartItemsDTO> createCategory(@RequestBody CartItemsDTO cartItemsDTO) {
        if (cartItemsRepository.existsByUserAndProduct(userRepository.findById(cartItemsDTO.getUser().getId()).get()
                , productRepository.findById(cartItemsDTO.getProduct().getId()).get()))
            throw new IllegalArgumentException("Sản phẩm này đã có trong giỏ hàng của bạn! ");
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
