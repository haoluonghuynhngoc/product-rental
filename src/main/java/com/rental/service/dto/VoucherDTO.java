package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.rental.domain.enums.VorcherStatus;
import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherDTO implements Serializable {

    private Long id;

    private Double discount;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private VorcherStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;


}
