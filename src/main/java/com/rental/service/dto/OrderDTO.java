package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.User;
import com.rental.domain.Voucher;
import com.rental.domain.enums.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;
    private Integer totalQuantity;
    private Double totalPrice;

    private OrderStatus status;

    private String createdBy;

    private String modifiedBy;

    private VoucherDTO voucher;

    private UserDTO user;

}
