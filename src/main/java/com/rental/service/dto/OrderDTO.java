package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.OrderDetails;
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
    private Double totalPrice;
    private String message;
    private String address;
    private String phone;
    private String name;
    private OrderDetailsDTO orderDetails ;
    private Long voucherId;
    private Long userId;

}
