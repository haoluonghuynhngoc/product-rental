package com.rental.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.rental.domain.Product;
import com.rental.domain.enums.UserStatus;
import com.rental.service.dto.UserDTO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rental.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);

    boolean existsByEmail(String email);

    boolean existsByUsername(String userName);

    List<User> findByLastNameLikeOrFirstNameLikeOrId(String lastName, String firstName, Long id);
}
