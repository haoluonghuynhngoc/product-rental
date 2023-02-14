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

    private Instant orderBrorrowDate;

    private Instant orderReturnDate;

    private OrderStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;

    private VoucherDTO voucher;

    private AccountDTO appUser;
<<<<<<< HEAD

=======
>>>>>>> d366af6133a04a2e3466686493bfced099525e4c



}
