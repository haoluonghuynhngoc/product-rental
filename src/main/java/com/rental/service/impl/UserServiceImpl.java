package com.rental.service.impl;

import java.util.Optional;

import com.rental.domain.Role;
import com.rental.domain.User;
import com.rental.domain.enums.RoleName;
import com.rental.repository.RoleRepository;
import com.rental.repository.UserRepository;
import com.rental.service.dto.ProductDTO;
import com.rental.service.dto.UserInfoDTO;
import com.rental.service.dto.UserPasswordDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.service.UserService;
import com.rental.service.dto.UserDTO;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public UserDTO create(UserDTO userDto) {
        User userEntity = userRepository.findUserByUserName(userDto.getUserName()).orElse(null);
        if (userEntity != null) {
            return null;
        } else {
            User user = modelMapper.map(userDto, User.class);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            System.out.println(user.getPassword());
            return modelMapper.map(userRepository.save(user), UserDTO.class);
        }
    }

    @Override
    @Transactional
    public UserDTO loginUsers(UserDTO userDto) {
        User userEntity = userRepository.findUserByUserName(userDto.getUserName()).orElse(null);
        if (userEntity != null) {
            if (bCryptPasswordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
               // userEntity.getRole().add(roleRepository.find);
                return modelMapper.map(userEntity, UserDTO.class);
            }
        }
        return null;
    }

    @Override
    public boolean removeUser(Long id) {
        boolean result = false;
        User userEntity = userRepository.findById(id).orElse(null);
        if (userEntity != null) {
            Role role = roleRepository.findByName(String.valueOf(RoleName.USERS)); // cần coi thêm
            userEntity.getRole().add(role);
            userRepository.deleteById(id);
            result = true;
        }
        return result;
    }

    @Override
    public Optional<UserInfoDTO> updateUser(UserInfoDTO UserInfoDTO, Long id) {
        return userRepository.findById(id).map(userEntity -> {
            modelMapper.map(UserInfoDTO, userEntity);
            userEntity.setId(id);
            return userEntity;
        }).map(userRepository::save).map(b -> {
            return modelMapper.map(b, UserInfoDTO.class);
        });
    }


    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(p -> {
            return modelMapper.map(p, UserDTO.class);
        });
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void changePassword(UserPasswordDTO userPasswordDTO) {
        User userEntity = userRepository.findUserByUserName(userPasswordDTO.getUserName()).orElseThrow(
                () -> new IllegalArgumentException("UserName is invalid !!! ")
        );
        if (!bCryptPasswordEncoder.matches(userPasswordDTO.getOldPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Old password is not correct ");
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode(userPasswordDTO.getNewPassword()));
        userRepository.save(userEntity);
    }


}
