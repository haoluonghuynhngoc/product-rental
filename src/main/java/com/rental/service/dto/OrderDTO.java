package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;

import com.rental.domain.enums.OrderStatus;
import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    private Integer totalQuantity;

    private Double totalPrice;

    private Instant orderBorrowDate; //sua ow day

    private Instant orderReturnDate;

    private OrderStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;

    private VoucherDTO voucher;

    private UserDTO appUser;

}
