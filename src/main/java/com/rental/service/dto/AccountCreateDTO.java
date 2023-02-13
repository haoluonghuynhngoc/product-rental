package com.rental.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountCreateDTO implements Serializable {
    private String userName;
    private String password;
    private String firstName;
    private String address;
    private String avatar;
    private Date birthDate;
    private String phone;
    private String lastName;
    private String email;
    private String imageUrl;
    private String createdBy;
    private String modifiedBy;
}
