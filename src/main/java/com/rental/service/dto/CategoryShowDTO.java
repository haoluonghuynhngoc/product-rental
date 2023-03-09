package com.rental.service.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class CategoryShowDTO {
    private Long id;
    private String name;
    private PagingResponse<ProductDTO> PageProducts ;
//    private Long id;
//    private String name;
//    private Set<ProductDTO> products = new HashSet<>();
}
