package com.rental.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private Integer quantity;

    private Double price;

    private OrderDTO order;

    private ProductDTO product;


}
