package com.rental.service.impl;

import com.rental.domain.CartItems;
import com.rental.domain.User;
import com.rental.repository.CartItemsRepository;
import com.rental.repository.CategoryRepository;
import com.rental.repository.ProductRepository;
import com.rental.repository.UserRepository;
import com.rental.service.CartItemsService;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemsServiceImpl implements CartItemsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartItemsDTO saveCart(CartItemsDTO cartItemsDTO) {
        cartItemsDTO.setProduct(modelMapper.map(
                productRepository.findById(cartItemsDTO.getProduct().getId()), ProductDTO.class
        ));
        cartItemsDTO.setUser(modelMapper.map(
                userRepository.findById(cartItemsDTO.getUser().getId()), UserDTO.class
        ));
        cartItemsDTO.setQuantity(1);
        return modelMapper.map(
                cartItemsRepository.save(modelMapper.map(cartItemsDTO, CartItems.class)), CartItemsDTO.class
        );
    }
    @Override
    public CartItemsShowDTO findAll(Long id) {
        CartItemsShowDTO cartItem = new CartItemsShowDTO();
        cartItem.setUser(modelMapper.map(userRepository.findById(id).get(),UserDTO.class));
        cartItemsRepository.findAllByUser(userRepository.findById(id).get()).forEach(
                i -> {
                    if (i.getProduct()!=null){
                        ProductCartDTO product = modelMapper.map(i.getProduct(),ProductCartDTO.class);
                        product.setIdCart(i.getId());
                        cartItem.getProduct().add(product);
                    }
                }
        );
        return cartItem;
    }

    @Override
    public Optional<CartItemsDTO> updateCartItem(CartItemsDTO cartItemsDTO) {
        return Optional.empty();
    }


    @Override
    public void delete(Long id) {
        cartItemsRepository.deleteById(id);
    }
}
