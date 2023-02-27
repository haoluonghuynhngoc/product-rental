package com.rental.service.dto;
import lombok.Data;
import java.io.Serializable;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CartItemsDTO implements Serializable {
    private Long id;
    private ProductDTO product;
    private UserDTO user;
    private Integer quantity;
}
