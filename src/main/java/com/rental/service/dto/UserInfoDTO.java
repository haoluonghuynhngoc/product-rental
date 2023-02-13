package com.rental.service.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserInfoDTO implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private String avatar;
    private String imageUrl;
    private Date birthDate;
    private String phone;
    private String email;
    private String createdBy;
    private String modifiedBy;
}
