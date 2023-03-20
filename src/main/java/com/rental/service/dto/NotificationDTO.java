package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.rental.domain.User;
import com.rental.domain.enums.NotificationStatus;
import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationDTO implements Serializable {

    private Long id;

    private String title;

    private String sortDescription;

    private String description;

    private Boolean isRead;

    private NotificationStatus status;

}
