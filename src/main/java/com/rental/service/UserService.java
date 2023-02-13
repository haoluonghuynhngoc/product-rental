package com.rental.service;

import java.util.Optional;

import com.rental.service.dto.UserInfoDTO;
import com.rental.service.dto.UserPasswordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.UserDTO;

public interface UserService {

    UserDTO create(UserDTO userDto);
    UserDTO loginUsers(UserDTO userDto);
    boolean removeUser(Long id);
    Optional<UserInfoDTO> updateUser(UserInfoDTO UserInfoDTO, Long id);
    Page<UserDTO> findAll(Pageable pageable);
    Optional<UserDTO> findOne(Long id);
    void changePassword(UserPasswordDTO userPasswordDTO) ;


}
