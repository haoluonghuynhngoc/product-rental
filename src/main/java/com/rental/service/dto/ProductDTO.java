package com.rental.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rental.domain.Image;
import com.rental.domain.enums.ProductStatus;
import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private Double price;
    private Double deposit;
    private String description;
    private Integer quantity;
    private ProductStatus status;
    private CategoryDTO category;
    private List<ImageDTO> images = new ArrayList<>();
  //  private UserDTO user;

}
