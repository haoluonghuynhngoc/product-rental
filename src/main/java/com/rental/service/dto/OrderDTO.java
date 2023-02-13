package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.rental.domain.OrderDetails;
import com.rental.domain.enums.OrderStatus;
import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    private Integer totalQuantity;

    private Double totalPrice;

    private Instant orderBrorrowDate;

    private Instant orderReturnDate;

    private OrderStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;

    private VoucherDTO voucher;

    private UserDTO appUser;



}
