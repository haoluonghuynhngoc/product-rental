package com.rental.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordChangeDTO implements Serializable {

   // private static final long serialVersionUID = 1L;

    private String currentPassword;
    private String newPassword;


}
