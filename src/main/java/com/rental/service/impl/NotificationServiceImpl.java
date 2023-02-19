package com.rental.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.rental.domain.Role;
import com.rental.domain.User;
import com.rental.repository.UserRepository;
import com.rental.service.dto.BrandDTO;
import com.rental.service.dto.UserDTO;
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
        Notification notification = modelMapper.map(notificationDTO, Notification.class);
        for (User user : userRepository.findAll()) {
            notification.getUsers().add(user);
            user.getNotifications().add(notification);
        }
        notification = notificationRepository.save(notification);
        return modelMapper.map(notification, NotificationDTO.class);
    }

//    @Override
//    public NotificationDTO update(NotificationDTO notificationDTO) {
//        Notification notification = modelMapper.map(notificationDTO, Notification.class);
//        notification = notificationRepository.save(notification);
//        return modelMapper.map(notification, NotificationDTO.class);
//    }

    @Override
    public Optional<NotificationDTO> updateNotification(NotificationDTO notificationDTO) {
        return notificationRepository.findById(notificationDTO.getId()).map(
                notificationEntity -> {
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
