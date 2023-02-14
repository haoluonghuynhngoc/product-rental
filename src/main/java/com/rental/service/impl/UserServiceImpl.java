package com.rental.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.service.UserService;
import com.rental.service.dto.UserDTO;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Override
    public UserDTO save(UserDTO applicationUserDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDTO update(UserDTO applicationUserDTO) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub

    }

}
