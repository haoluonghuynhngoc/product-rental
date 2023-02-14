package com.rental.service.impl;

import java.util.Optional;

import com.rental.domain.Role;
import com.rental.domain.enums.RoleName;
import com.rental.repository.RoleRepository;
import com.rental.service.dto.AccountCreateDTO;
import com.rental.service.dto.AccountInfoDTO;
import com.rental.service.dto.AccountPasswordDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.service.AccountService;
import com.rental.service.dto.AccountDTO;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Override
    public AccountDTO createUser(AccountCreateDTO accountCreateDTO) {
        return null;
    }

    @Override
    public AccountDTO createAdmin(AccountCreateDTO accountCreateDTO) {
        return null;
    }

    @Override
    public AccountDTO loginUsers(AccountCreateDTO AccountCreateDTO) {
        return null;
    }

    @Override
    public boolean removeUser(Long id) {
        return false;
    }

    @Override
    public Optional<AccountInfoDTO> updateUser(AccountInfoDTO accountInfoDTO, Long id) {
        return Optional.empty();
    }

    @Override
    public Page<AccountDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<AccountDTO> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void changePassword(AccountPasswordDTO accountPasswordDTO) {

    }
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private AccountRepository accountRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//    @Autowired
//    private PasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    @Transactional
//    public AccountDTO createUser(AccountCreateDTO accountCreateDTO) {
//        Account userEntity = accountRepository.findUserByUserName(accountCreateDTO.getUserName()).orElse(null);
//        if (userEntity != null) {
//            return null;
//        } else {
//            Role roleEntity = roleRepository.findByName(String.valueOf(RoleName.USERS));
//            Account user = modelMapper.map(accountCreateDTO, Account.class);
//            user.getRole().add(roleEntity);
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            return modelMapper.map(accountRepository.save(user), AccountDTO.class);
//        }
//    }
//    @Override
//    @Transactional
//    public AccountDTO createAdmin(AccountCreateDTO accountCreateDTO) {
//        Account userEntity = accountRepository.findUserByUserName(accountCreateDTO.getUserName()).orElse(null);
//        if (userEntity != null) {
//            return null;
//        } else {
//            Role roleEntity = roleRepository.findByName(String.valueOf(RoleName.ADMIN));
//            Account user = modelMapper.map(accountCreateDTO, Account.class);
//            user.getRole().add(roleEntity);
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            return modelMapper.map(accountRepository.save(user), AccountDTO.class);
//        }
//    }
//
//    @Override
//    @Transactional
//    public AccountDTO loginUsers(AccountCreateDTO AccountCreateDTO) {
//        Account userEntity = accountRepository.findUserByUserName(AccountCreateDTO.getUserName()).orElse(null);
//        if (userEntity != null) {
//            if (bCryptPasswordEncoder.matches(AccountCreateDTO.getPassword(), userEntity.getPassword())) {
//                return modelMapper.map(userEntity, AccountDTO.class);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean removeUser(Long id) {
//        boolean result = false;
//        Account userEntity = accountRepository.findById(id).orElse(null);
//        if (userEntity != null) {
//            Role role = roleRepository.findByName(String.valueOf(RoleName.USERS)); // cần coi thêm
//            userEntity.getRole().add(role);
//            accountRepository.deleteById(id);
//            result = true;
//        }
//        return result;
//    }
//
//    @Override
//    public Optional<AccountInfoDTO> updateUser(AccountInfoDTO accountInfoDTO, Long id) {
//        return accountRepository.findById(id).map(userEntity -> {
//            modelMapper.map(accountInfoDTO, userEntity);
//            userEntity.setId(id);
//            return userEntity;
//        }).map(accountRepository::save).map(b -> {
//            return modelMapper.map(b, AccountInfoDTO.class);
//        });
//    }
//
//
//    @Override
//    public Page<AccountDTO> findAll(Pageable pageable) {
//        return accountRepository.findAll(pageable).map(p -> {
//            return modelMapper.map(p, AccountDTO.class);
//        });
//    }
//
//    @Override
//    public Optional<AccountDTO> findOne(Long id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public void changePassword(AccountPasswordDTO accountPasswordDTO) {
//        Account userEntity = accountRepository.findUserByUserName(accountPasswordDTO.getUserName()).orElseThrow(
//                () -> new IllegalArgumentException("UserName is invalid !!! ")
//        );
//        if (!bCryptPasswordEncoder.matches(accountPasswordDTO.getOldPassword(), userEntity.getPassword())) {
//            throw new IllegalArgumentException("Old password is not correct ");
//        }
//        userEntity.setPassword(bCryptPasswordEncoder.encode(accountPasswordDTO.getNewPassword()));
//        accountRepository.save(userEntity);
//    }


}
