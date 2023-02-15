package com.rental.web.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
@RequestMapping("/api")
public class NotificationResource {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;


    @PostMapping("/notifications")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {

        if (notificationDTO.getId() != 0) {
            throw new IllegalArgumentException("A new notification cannot already have an ID ");
        }
        NotificationDTO result = notificationService.save(notificationDTO);
        return ResponseEntity.ok().body(result);

    }

    @PutMapping("/notifications/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody NotificationDTO notificationDTO) {

        if (notificationDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id : idnull");
        }
        if (!Objects.equals(id, notificationDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID : idinvalid");
        }

        if (!notificationRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found : idnotfound");
        }

        NotificationDTO result = notificationService.update(notificationDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/notifications/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<NotificationDTO> partialUpdateNotification(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody NotificationDTO notificationDTO) {
        if (notificationDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id : idnull");
        }
        if (!Objects.equals(id, notificationDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID : idinvalid");
        }

        if (!notificationRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found : idnotfound");
        }

        Optional<NotificationDTO> result = notificationService.partialUpdate(notificationDTO);

        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<NotificationDTO> page = notificationService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable Long id) {
        Optional<NotificationDTO> notificationDTO = notificationService.findOne(id);
        return notificationDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
