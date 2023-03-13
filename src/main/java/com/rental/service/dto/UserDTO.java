package com.rental.service.dto;

import com.rental.domain.Notification;
import com.rental.domain.Order;
import com.rental.domain.Product;
import com.rental.domain.Role;
import com.rental.domain.enums.UserStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String address;
    private String avatar;
    private Date birthday;
    private String phone;
    private UserStatus status;
    private String lastName;
    private String email;
    private String imageUrl;
    private Set<Role> role = new HashSet<>();
    private Set<NotificationDTO> notifications = new HashSet<>();
    private Set<OrderDTO> orders = new HashSet<>();

}
