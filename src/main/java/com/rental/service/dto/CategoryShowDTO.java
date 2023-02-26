package com.rental.service.dto;

import com.rental.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Builder
@AllArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class CategoryShowDTO implements Serializable {
    private Long id;
    private String name;
    private Set<ProductDTO> products = new HashSet<>();
}
