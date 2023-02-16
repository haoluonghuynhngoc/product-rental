package com.rental.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class BrandDTO implements Serializable {
    private Long id;
    private String name;

}
