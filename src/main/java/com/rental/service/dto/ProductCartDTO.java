package com.rental.service.dto;

import com.rental.domain.enums.ProductStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductCartDTO {
    private Long id;
    private Long idCart;
    private String name;
    private Double price;
    private Double deposit;
    private String description;
    private Integer quantity;
    private ProductStatus status;
    private CategoryDTO category;
    private List<ImageDTO> images = new ArrayList<>();
}
