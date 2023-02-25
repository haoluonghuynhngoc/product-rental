package com.rental.service;

import java.util.Optional;

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
}
