package com.rental.service.dto;

import com.rental.domain.User;
import com.rental.domain.enums.BlogStatus;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class BlogDTO implements Serializable {

    private Long id;
    private String imageTitle;
    private String imageCover;
    private String title;
    private String author;
    private String description;
    private Instant createdDate;
    private BlogStatus status;
    private UserDTO user;
}
