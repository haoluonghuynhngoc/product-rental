package com.rental.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CartItemsShowDTO {
    private UserDTO user;
    private List<ProductCartDTO> product = new ArrayList<>();

}
