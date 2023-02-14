package com.rental.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordChangeDTO implements Serializable {

    private String userName;
    private String currentPassword;
    private String newPassword;

}
