package com.rental.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.repository.UserRepository;
import com.rental.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class UserResource {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


}
