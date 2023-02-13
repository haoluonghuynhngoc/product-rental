package com.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.domain.Role;


public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);

}
