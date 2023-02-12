package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;

import com.rental.domain.enums.NotificationStatus;
import lombok.Data;

@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificationDTO implements Serializable {

    private Long id;

    private String title;

    private String sortDescripsion;

    private String description;

    private Boolean isRead;

    private NotificationStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;

}
