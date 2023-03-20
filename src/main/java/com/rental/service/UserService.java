package com.rental.service;

import java.util.List;
import java.util.Optional;

import com.rental.domain.User;
import com.rental.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service class for managing users.
 */

public interface UserService {

    UserDTO createUser(UserDTO applicationUserDTO);
    Optional<UserDTO> updateUser(UserDTO applicationUserDTO);
    Optional<UserDTO> updateUserStatus(UserDTO applicationUserDTO);
    UserDTO loginUser(UserDTO applicationUserDTO);
    Page<UserShowDTO> findAll(Pageable pageable);
    PagingResponse<UserShowDTO> searchUserByFirstName(String firstName, Pageable pageable);
    UserDTO changePassword(PasswordChangeDTO changePassword) ;
    Optional<UserShowDTO> findOne(Long id);
    void delete(Long id);
    public  List<UserStatisticsDTO> statisticUserByYear(int year);
}
