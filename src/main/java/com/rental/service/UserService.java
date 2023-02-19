package com.rental.service;

import java.util.Optional;

import com.rental.domain.User;
import com.rental.service.dto.PasswordChangeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.UserDTO;

/**
 * Service class for managing users.
 */

public interface UserService {

    UserDTO createUser(UserDTO applicationUserDTO);
    Optional<UserDTO> updateUser(UserDTO applicationUserDTO);
    UserDTO loginUser(UserDTO applicationUserDTO);
    Page<UserDTO> findAll(Pageable pageable);
    UserDTO changePassword(PasswordChangeDTO changePassword) ;
    Optional<UserDTO> findOne(Long id);
    void delete(Long id);
}
