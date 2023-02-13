package com.rental.repository;

import com.rental.domain.User;
import com.rental.domain.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.domain.Role;


public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);

}
