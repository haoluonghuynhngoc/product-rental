package com.rental.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.OrderDetails;
import com.rental.domain.User;
import com.rental.domain.Voucher;
import com.rental.domain.enums.OrderStatus;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderShowDTO {
    private Long id;
    private Integer totalQuantity;
    private Double totalPrice;
    private OrderStatus status;
    private String message;
    private String address;
    private String phone;
    private String name;
    private VoucherDTO voucher;
    private UserDTO user;
    private Set<OrderDetailShowDTO> orderDetails = new HashSet<>();
}
