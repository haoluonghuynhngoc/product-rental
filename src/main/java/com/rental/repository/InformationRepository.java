package com.rental.repository;

import com.rental.domain.Information;
import com.rental.domain.Order;
import com.rental.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface InformationRepository extends JpaRepository<Information,Long> {

    Stream<Information> findAllByUser(User user);
    Stream<Information> findByUserAndOrder(User user , Order order);
}
