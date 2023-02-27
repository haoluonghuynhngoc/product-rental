package com.rental.service.impl;

import com.rental.domain.CartItems;
import com.rental.domain.User;
import com.rental.repository.CartItemsRepository;
import com.rental.repository.CategoryRepository;
import com.rental.repository.ProductRepository;
import com.rental.repository.UserRepository;
import com.rental.service.CartItemsService;
import com.rental.service.dto.CartItemsDTO;
import com.rental.service.dto.CategoryDTO;
import com.rental.service.dto.ProductDTO;
import com.rental.service.dto.UserDTO;
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
    public List<CartItemsDTO> findAll(Long id) {
        List<CartItemsDTO> list = new ArrayList<>();
        cartItemsRepository.findAllByUser(userRepository.findById(id).get()).forEach(
                i -> list.add(modelMapper.map(i, CartItemsDTO.class))
        );
        return list;


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
