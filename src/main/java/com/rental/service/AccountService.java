package com.rental.service;

import java.util.Optional;

import com.rental.service.dto.AccountCreateDTO;
import com.rental.service.dto.AccountInfoDTO;
import com.rental.service.dto.AccountPasswordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.AccountDTO;

public interface AccountService {

    AccountDTO createUser(AccountCreateDTO accountCreateDTO);
    AccountDTO createAdmin(AccountCreateDTO accountCreateDTO);
    AccountDTO loginUsers(AccountCreateDTO AccountCreateDTO);
    boolean removeUser(Long id);
    Optional<AccountInfoDTO> updateUser(AccountInfoDTO accountInfoDTO, Long id);
    Page<AccountDTO> findAll(Pageable pageable);
    Optional<AccountDTO> findOne(Long id);
    void changePassword(AccountPasswordDTO accountPasswordDTO) ;


}
