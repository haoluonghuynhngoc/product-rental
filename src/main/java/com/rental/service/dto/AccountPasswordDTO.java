package com.rental.service.dto;

import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountPasswordDTO {
    private String userName;
    private String oldPassword;
    private String newPassword;
}
