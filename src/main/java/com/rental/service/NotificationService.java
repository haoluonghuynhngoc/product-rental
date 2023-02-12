package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.NotificationDTO;

public interface NotificationService {

    NotificationDTO save(NotificationDTO notificationDTO);

    NotificationDTO update(NotificationDTO notificationDTO);

    Optional<NotificationDTO> partialUpdate(NotificationDTO notificationDTO);

    Page<NotificationDTO> findAll(Pageable pageable);

    Optional<NotificationDTO> findOne(Long id);

    void delete(Long id);
}
