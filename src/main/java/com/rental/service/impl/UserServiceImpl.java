package com.rental.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.rental.domain.Order;
import com.rental.domain.Role;
import com.rental.domain.User;
import com.rental.domain.enums.RoleName;
import com.rental.domain.enums.UserStatus;
import com.rental.repository.ProductRepository;
import com.rental.repository.RoleRepository;
import com.rental.repository.UserRepository;
import com.rental.service.dto.BrandDTO;
import com.rental.service.dto.PasswordChangeDTO;
import com.rental.service.dto.ProductDTO;
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
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO applicationUserDTO) {
        // nếu toàn có thẻ nhập id thì hãy xóa vì rold là dạng enum bên phải kiểu là ADMIN hoặc USER
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findByName(RoleName.USERS));
        applicationUserDTO.setRole(role);
        applicationUserDTO.setStatus(UserStatus.UNLOCKED);
        applicationUserDTO.setPassword(bCryptPasswordEncoder.encode(applicationUserDTO.getPassword()));
        return modelMapper.map(userRepository.save(modelMapper.map(applicationUserDTO, User.class)), UserDTO.class);
    }

    @Override
    public Optional<UserDTO> updateUser(UserDTO applicationUserDTO) {
        return userRepository.findById(applicationUserDTO.getId()).map(
                userEntity -> {
                    applicationUserDTO.setUsername(userEntity.getUsername());
                    applicationUserDTO.setPassword(userEntity.getPassword());
                    applicationUserDTO.setRole(userEntity.getRole());
                    modelMapper.map(applicationUserDTO, userEntity);
                    return userEntity;
                }
        ).map(userRepository::save).map(b -> {
            return modelMapper.map(b, UserDTO.class);
        });
    }

    @Override
    public UserDTO loginUser(UserDTO applicationUserDTO) {
        User userEntity = userRepository.findByUsername(applicationUserDTO.getUsername());
//        if (userEntity == null)
//            throw new IllegalArgumentException("User name is invalid");
//        if (!bCryptPasswordEncoder.matches(applicationUserDTO.getPassword(), userEntity.getPassword()))
//            throw new IllegalArgumentException("Password is not correct");
        if (userEntity == null||!bCryptPasswordEncoder.matches(applicationUserDTO.getPassword(), userEntity.getPassword()))
            throw new IllegalArgumentException("Sai tài khoảng hoặc mật khẩu ");
        if (applicationUserDTO.getStatus() == UserStatus.LOCKED)
            throw new IllegalArgumentException("Tài khoảng của bạn đã bị khóa ");
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(p -> {
            return modelMapper.map(p, UserDTO.class);
        });
    }

    @Override
    public User changePassword(PasswordChangeDTO changePassword) {
        User userEntity = userRepository.findByUsername(changePassword.getUserName());
        if (!bCryptPasswordEncoder.matches(changePassword.getCurrentPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Current password is invalid ");
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userRepository.findById(id).map(
                user -> modelMapper.map(user, UserDTO.class)
        );
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
