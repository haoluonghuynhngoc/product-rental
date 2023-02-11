package com.rental.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.repository.UserRepository;
import com.rental.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class UserResource {

    private final UserService userService;

    private final UserRepository userRepository;

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

    }

}
