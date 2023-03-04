package com.rental.service.dto;

import com.rental.domain.Product;
import lombok.Data;

import java.util.Date;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDetailShowDTO {
    private Long id;
    private Date orderBorrowDate;
    private Date orderReturnDate;
    private String imageBorrow;
    private Integer quantity;
    private Double price;
    private Double deposit;
    private String imageReturn;
    private ProductDTO product;
}
