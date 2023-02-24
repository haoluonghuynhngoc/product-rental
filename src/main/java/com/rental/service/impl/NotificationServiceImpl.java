package com.rental.service.impl;

import java.util.Optional;

import com.rental.domain.User;
import com.rental.domain.enums.NotificationStatus;
import com.rental.repository.UserRepository;
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
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationDTO) {
        notificationDTO.setStatus(NotificationStatus.PENDING);
        Notification notification = modelMapper.map(notificationDTO, Notification.class);
//        for (User user : userRepository.findAll()) {
//            notification.getUsers().add(user);
//            user.getNotifications().add(notification);
//        }
        notification = notificationRepository.save(notification);
        return modelMapper.map(notification, NotificationDTO.class);
    }

    @Override
    public Optional<NotificationDTO> updateNotification(NotificationDTO notificationDTO) {
        return notificationRepository.findById(notificationDTO.getId()).map(
                notificationEntity -> {
                    if (notificationDTO.getStatus().equals(NotificationStatus.APPROVED)) {
                        for (User user : userRepository.findAll()) {
                            notificationEntity.getUsers().add(user);
                            user.getNotifications().add(notificationEntity);
                        }
                    } else if (notificationDTO.getStatus().equals(NotificationStatus.REJECTED)) {
                        for (User user : userRepository.findAll()) {
                            notificationEntity.getUsers().remove(user);
                            user.getNotifications().remove(notificationEntity);
                        }
                    }
                    modelMapper.map(notificationDTO, notificationEntity);
                    return notificationEntity;
                }).map(notificationRepository::save).map(
                n -> {
                    return modelMapper.map(n, NotificationDTO.class);
                }
        );
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
// delete chưa xong
        notificationRepository.deleteById(id);
    }
}
