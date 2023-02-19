package com.rental.web.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.rental.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rental.repository.NotificationRepository;
import com.rental.service.NotificationService;
import com.rental.service.dto.NotificationDTO;

@RestController
@RequestMapping("/api/notification")
public class NotificationResource {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;


    @PostMapping("/create")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        if (notificationDTO.getId() != null)
            throw new IllegalArgumentException("A new notification cannot already have an ID ");
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.createNotification(notificationDTO));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(
            @RequestBody NotificationDTO notificationDTO) {
        if (notificationDTO.getId() == null)
            throw new IllegalArgumentException("Invalid id : id is null");
        if (!notificationRepository.existsById(notificationDTO.getId()))
            throw new IllegalArgumentException("Entity not found : id is not found");
        return notificationService.updateNotification(notificationDTO).map(
                notificationData -> ResponseEntity.status(HttpStatus.OK).body(notificationData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not update notification")
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<NotificationDTO> notifications = notificationService.findAll(pageable);
        if (notifications.isEmpty())
            throw new IllegalArgumentException("Page in over size notification ");
        return ResponseEntity.status(HttpStatus.OK).body(notifications.getContent());

    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
        if (!notificationRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the notification have id " + id + " in the data ");
        //Optional<NotificationDTO> notificationDTO = notificationService.findOne(id);
        return notificationService.findOne(id).map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElseThrow(() -> new IllegalArgumentException("Cant not find the notification"));
    }

    // chua sua
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        if (!notificationRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the Id :" + id + "In the data ")
        notificationService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }
}
