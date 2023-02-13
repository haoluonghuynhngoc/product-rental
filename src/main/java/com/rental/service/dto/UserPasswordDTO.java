package com.rental.service.dto;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserPasswordDTO {
    private String userName;
    private String oldPassword;
    private String newPassword;
}
