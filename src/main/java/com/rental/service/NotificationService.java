package com.rental.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.rental.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.NotificationDTO;

public interface NotificationService {

    NotificationDTO createNotification(NotificationDTO notificationDTO);

    Optional<NotificationDTO> updateNotification(NotificationDTO notificationDTO);

    Page<NotificationDTO> findAll(Pageable pageable);

    Optional<NotificationDTO> findOne(Long id);

    void delete(Long id);
    List<NotificationDTO> getAllNotificationByUser(Long id);
    public Integer findALLOrderIsRead(Long id);
}
