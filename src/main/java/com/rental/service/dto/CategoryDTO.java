package com.rental.service.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * A DTO for the {@link com.swp391.domain.Category} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class CategoryDTO implements Serializable {

    private Long id;

    private String name;

}
