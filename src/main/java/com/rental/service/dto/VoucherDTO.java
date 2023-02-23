package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import com.rental.domain.enums.VorcherStatus;
import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherDTO implements Serializable {

    private Long id;
    private Double discount;
    private String name;
    private Date startDate;
    private Date endDate;
    private VorcherStatus status;
//    private String createdBy;
//    private String modifiedBy;


}
