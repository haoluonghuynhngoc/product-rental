package com.rental.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.rental.domain.Product;
import com.rental.domain.Role;
import com.rental.domain.User;
import com.rental.domain.enums.RoleName;
import com.rental.domain.enums.UserStatus;
import com.rental.repository.ProductRepository;
import com.rental.repository.RoleRepository;
import com.rental.repository.UserRepository;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rental.service.UserService;

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


    @Override
    @Transactional
    public UserDTO createUser(UserDTO applicationUserDTO) {
        Set<Role> role = new HashSet<>();
        role.add(roleRepository.findByName(RoleName.USERS));
        applicationUserDTO.setId(-1L);
        applicationUserDTO.setFirstName(applicationUserDTO.getUsername()); // tên người dùng khi tạo phải trùng với UserName
        applicationUserDTO.setRole(role);
        applicationUserDTO.setStatus(UserStatus.UNLOCKED);
        applicationUserDTO.setPassword(bCryptPasswordEncoder.encode(applicationUserDTO.getPassword()));
        return modelMapper.map(userRepository.save(modelMapper.map(applicationUserDTO, User.class)), UserDTO.class);
    }

    @Override
    public Optional<UserDTO> updateUser(UserDTO applicationUserDTO) {
        return userRepository.findById(applicationUserDTO.getId()).map(
                userEntity -> {
                    if (applicationUserDTO.getFirstName()==null||applicationUserDTO.getFirstName().isEmpty())
                        applicationUserDTO.setFirstName(userEntity.getFirstName());
                    if (applicationUserDTO.getAddress()==null||applicationUserDTO.getAddress().isEmpty())
                        applicationUserDTO.setAddress(userEntity.getAddress());
                    if (applicationUserDTO.getAvatar()==null||applicationUserDTO.getAvatar().isEmpty())
                        applicationUserDTO.setAvatar(userEntity.getAvatar());
                    if (applicationUserDTO.getBirthday()==null)
                        applicationUserDTO.setBirthday(userEntity.getBirthday());
                    if (applicationUserDTO.getPhone()==null||applicationUserDTO.getPhone().isEmpty())
                        applicationUserDTO.setPhone(userEntity.getPhone());
                    if (applicationUserDTO.getLastName()==null||applicationUserDTO.getLastName().isEmpty())
                        applicationUserDTO.setLastName(userEntity.getLastName());
                    if (applicationUserDTO.getImageUrl()==null||applicationUserDTO.getImageUrl().isEmpty())
                        applicationUserDTO.setImageUrl(userEntity.getImageUrl());
                    if (applicationUserDTO.getEmail() == null||applicationUserDTO.getEmail().isEmpty())
                        applicationUserDTO.setEmail(userEntity.getEmail());
                    applicationUserDTO.setUsername(userEntity.getUsername());
                    applicationUserDTO.setPassword(userEntity.getPassword());
                    applicationUserDTO.setRole(userEntity.getRole());
                    applicationUserDTO.setStatus(userEntity.getStatus());
                    modelMapper.map(applicationUserDTO, userEntity);
                    return userEntity;
                }
        ).map(userRepository::save).map(b -> {
            return modelMapper.map(b, UserDTO.class);
        });
    }

    @Override
    public Optional<UserDTO> updateUserStatus(UserDTO applicationUserDTO) {
        return userRepository.findById(applicationUserDTO.getId()).map(
                userEntity -> {
                    userEntity.setStatus(applicationUserDTO.getStatus());
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
        if (userEntity.getStatus().equals(UserStatus.REJECTED))
            throw new IllegalArgumentException("Tài khoản đã bị xóa vui lòng liên hệ admin để lấy lại tài khoản");
        if (userEntity.getStatus().equals(UserStatus.LOCKED))
            throw new IllegalArgumentException("Tài khoản của bạn đã bị khóa");
        if (!bCryptPasswordEncoder.matches(applicationUserDTO.getPassword(), userEntity.getPassword()))
            throw new IllegalArgumentException("Sai tài khoản hoặc mật khẩu");
        return modelMapper.map(userEntity, UserDTO.class);
    }

    @Override
    public Page<UserShowDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(p -> {
            return modelMapper.map(p, UserShowDTO.class);
        });
    }

    @Override
    public PagingResponse<UserShowDTO> searchUserByFirstName(String name, Pageable pageable) {
        long id = -1L;
        try {
            id = Long.parseLong(name);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        List<UserShowDTO> listDTO = new ArrayList<>();
        userRepository.findByLastNameLikeOrFirstNameLikeOrId("%" + name + "%", "%" + name + "%", id).forEach(
                user -> listDTO.add(modelMapper.map(user, UserShowDTO.class))
        );
        final int startPage = (int) pageable.getOffset();
        final int endPage = Math.min((startPage + pageable.getPageSize()), listDTO.size());
        Page<UserShowDTO> pageProduct = new PageImpl<>(listDTO.subList(startPage, endPage), pageable, listDTO.size());
        return PagingResponse.<UserShowDTO>builder()
                .page(pageProduct.getPageable().getPageNumber() + 1)
                .size(pageProduct.getSize())
                .totalPage(pageProduct.getTotalPages())
                .totalItem(pageProduct.getTotalElements())
                .contends(pageProduct.getContent())
                .build();
    }

    @Override
    public UserDTO changePassword(PasswordChangeDTO changePassword) {
        User userEntity = userRepository.findByUsername(changePassword.getUserName());
        if (!bCryptPasswordEncoder.matches(changePassword.getCurrentPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu hiện tại không đúng ");
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));
        return modelMapper.map(userRepository.save(userEntity), UserDTO.class);
    }

    @Override
    public Optional<UserShowDTO> findOne(Long id) {
        return userRepository.findById(id).map(
                user -> modelMapper.map(user, UserShowDTO.class)
        );
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserStatisticsDTO> statisticUserByYear(int year) {
        List<UserStatisticsDTO> listUserStatistics = new ArrayList<>();
        Map<Integer, List<User>> monthUser = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthUser.put(i, new ArrayList<>());
        }
        List<User> listUserYear = userRepository.findAll().stream().filter(u ->
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(u.getCreatedDate()));
            return calendar.get(Calendar.YEAR) == year; // đúng năm thì nó lấy k đúng năm thì không lấy
        }).collect(Collectors.toList());

        listUserYear.forEach(user -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Date.from(user.getCreatedDate()));
            int month =calendar.get(Calendar.MONTH) + 1;
            for (int i = 1; i <= 12; i++) {
                if (month == i) {
                    monthUser.get(i).add(user);
                }
            }
        });

        monthUser.forEach((k, v) -> {
            int totalAccountLocked = 0;
            int totalAccountUnLocked = 0;
            for (User user : v) {
                if (user.getStatus().equals(UserStatus.LOCKED))
                    totalAccountLocked++;
                else if (user.getStatus().equals(UserStatus.UNLOCKED))
                    totalAccountUnLocked++;
            }
            listUserStatistics.add(UserStatisticsDTO.builder()
                    .month(k)
                    .totalUser(v.size())
                    //.totalAccountLocked(totalAccountLocked)
                    //.totalAccountUnLocked(totalAccountUnLocked)
                    .build());
        });
        return listUserStatistics;
    }
}
