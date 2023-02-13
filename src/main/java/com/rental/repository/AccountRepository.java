package com.rental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rental.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findUserByUserName(String userName);
    Optional<Account> findFirstByAddress(String id);
}
