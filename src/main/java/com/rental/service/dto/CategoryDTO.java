package com.rental.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.rental.domain.Product;
import lombok.Data;

@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
   // private Set<ProductDTO> products = new HashSet<>();
}
