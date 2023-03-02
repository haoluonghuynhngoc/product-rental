package com.rental.service.dto;

import com.rental.domain.enums.UserStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserImageDTO {
    private Long id;
    private String firstName;
    private String address;
   // private MultipartFile localAvatar;
    private Date birthday;
    private String phone;
    private String lastName;
    private String email;
   // private MultipartFile localImageUrl;
}
