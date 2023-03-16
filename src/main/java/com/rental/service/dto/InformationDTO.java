package com.rental.service.dto;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InformationDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean isRead;
    private Instant createdDate;
    private UserDTO user;
    private OrderDTO order;
}
