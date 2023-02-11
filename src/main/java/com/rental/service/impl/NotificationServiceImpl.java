package com.rental.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.domain.Notification;
import com.rental.repository.NotificationRepository;
import com.rental.service.NotificationService;
import com.rental.service.dto.NotificationDTO;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }

    @Override
    public NotificationDTO save(NotificationDTO notificationDTO) {

        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        notification = notificationRepository.save(notification);
        return modelMapper.map(notification, NotificationDTO.class);
    }

    @Override
    public NotificationDTO update(NotificationDTO notificationDTO) {

        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        notification = notificationRepository.save(notification);
        return modelMapper.map(notification, NotificationDTO.class);
    }

    @Override
    public Optional<NotificationDTO> partialUpdate(NotificationDTO notificationDTO) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationDTO> findAll(Pageable pageable) {

        return notificationRepository.findAll(pageable).map(n -> {
            return modelMapper.map(n, NotificationDTO.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationDTO> findOne(Long id) {

        return notificationRepository.findById(id).map(n -> {
            return modelMapper.map(n, NotificationDTO.class);
        });
    }

    @Override
    public void delete(Long id) {

        notificationRepository.deleteById(id);
    }
}
