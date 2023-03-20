package com.rental.repository;

import com.rental.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.rental.domain.Notification;

import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("unused")
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Stream<Notification> findByUsers(User user);
}
