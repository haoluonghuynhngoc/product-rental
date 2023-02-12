package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
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

    private String description;

    private String sortDescription;

    private Integer quantity;

    private ProductStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;

    private BrandDTO brand;

    private CategoryDTO category;

    private Set<Image> images;


}
