package com.rental.service.dto;

import com.rental.domain.enums.ProductStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductImageDTO {
    private Long id;
    private String name;
    private Double price;
    private Double deposit;
    private String description;
    private Integer quantity;
    private ProductStatus status;
    private Long categoryId;
    private List<MultipartFile> localImage;
}
