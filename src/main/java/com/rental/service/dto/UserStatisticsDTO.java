package com.rental.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserStatisticsDTO {
    private int month;
    private int totalUser;
    private int totalAccountLocked;
    private int totalAccountUnLocked;
}
