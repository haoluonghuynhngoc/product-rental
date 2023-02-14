package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.UserDTO;

/**
 * Service class for managing users.
 */

public interface UserService {

    UserDTO save(UserDTO applicationUserDTO);

    UserDTO update(UserDTO applicationUserDTO);

    Page<UserDTO> findAll(Pageable pageable);

    Optional<UserDTO> findOne(Long id);

    void delete(Long id);
}
