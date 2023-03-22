package com.rental.web.rest;

import java.util.List;
import com.rental.repository.UserRepository;
import com.rental.service.dto.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.createNotification(notificationDTO));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(
            @RequestBody NotificationDTO notificationDTO) {
        if (notificationDTO.getId() == null)
            throw new IllegalArgumentException("Cập nhật thông báo cần có ID");
        if (!notificationRepository.existsById(notificationDTO.getId()))
            throw new IllegalArgumentException("Không thể tìm thấy thông báo có Id :" + notificationDTO.getId());
        return notificationService.updateNotification(notificationDTO).map(
                notificationData -> ResponseEntity.status(HttpStatus.OK).body(notificationData)).orElseThrow(
                () -> new IllegalArgumentException("Không thể cập nhật thông báo")
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<PagingResponse<NotificationDTO>> getAllNotifications(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<NotificationDTO> pageNotification = notificationService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
                PagingResponse.<NotificationDTO>builder()
                        .page(pageNotification.getPageable().getPageNumber() + 1)
                        .size(pageNotification.getSize())
                        .totalPage(pageNotification.getTotalPages())
                        .totalItem(pageNotification.getTotalElements())
                        .contends(pageNotification.getContent())
                        .build());
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
            throw new IllegalArgumentException("Cant not find the Id :" + id + "In the data ");
        notificationService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @GetMapping("/getAllByUser/{id}")
    public ResponseEntity<List<NotificationDTO>> getAllNotificationByUser(@PathVariable(name = "id") Long id) {
        if (!userRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy người dùng có id :"+id);
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getAllNotificationByUser(id) );
    }

    @GetMapping("/getCountIsRead/{id}")
    public ResponseEntity<Integer> getCountRead(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notificationService.findALLOrderIsRead(id));
    }
}
