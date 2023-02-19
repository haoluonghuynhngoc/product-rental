package com.rental.service.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDetailsDTO implements Serializable {

    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private Date orderBorrowDate;
    private Date orderReturnDate;
    private String imageBorrow;
    private String imageReturn;
    private OrderDTO order;
    private ProductDTO product;


}
