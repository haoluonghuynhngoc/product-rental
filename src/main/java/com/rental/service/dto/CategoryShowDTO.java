package com.rental.service.dto;

import com.rental.domain.Product;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class CategoryShowDTO implements Serializable {
    private Long id;
    private String name;
    private Set<ProductDTO> products = new HashSet<>();
}
