package com.rental.service.dto;

import com.rental.domain.enums.InformationStatus;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InformationShowDTO {
    private Long id;
    private String title;
    private String image;
    private String description;
    private Boolean isRead;
    private InformationStatus status;
    private Instant createdDate;
    //private UserDTO user;
    private OrderShowDTO order;
}