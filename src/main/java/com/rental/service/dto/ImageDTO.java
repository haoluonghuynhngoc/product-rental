package com.rental.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImageDTO implements Serializable {

    private Long id;

    private String name;

    private String url;

    private ProductDTO product;


}
