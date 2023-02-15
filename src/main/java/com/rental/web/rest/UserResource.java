package com.rental.web.rest;

import com.rental.domain.User;
import com.rental.repository.UserRepository;
import com.rental.service.UserService;
import com.rental.service.dto.ImageDTO;
import com.rental.service.dto.PasswordChangeDTO;
import com.rental.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO users) {
        if (userRepository.findByUsername(users.getUsername())!=null)
            throw new IllegalArgumentException("UserId vace in data  ");
        if (users.getId() != 0)
            throw new IllegalArgumentException("Id must be 0 ");
        if (users.getUsername().isEmpty())
            throw new IllegalArgumentException("UserName is empty ");
        if (users.getPassword().isEmpty())
            throw new IllegalArgumentException("Password is empty");
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(users));

    }

    @PostMapping("/login") // login loi
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO users) {
        if (users.getUsername().isEmpty())
            throw new IllegalArgumentException("UserName is empty ");
        if (users.getPassword().isEmpty())
            throw new IllegalArgumentException("Password is empty");

        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(users));
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        if (userRepository.findById(userDTO.getId()).isPresent())
            throw new IllegalArgumentException("Cant not find the User");
        return userService.updateUser(userDTO).map(userData -> ResponseEntity.status(HttpStatus.OK).body(userData)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changeUserPassword(@RequestBody PasswordChangeDTO changePassword) {
        if (userRepository.findByUsername(changePassword.getUserName()) == null)
            throw new IllegalArgumentException("UserName is invalid ");
        if (changePassword.getCurrentPassword().isEmpty())
            throw new IllegalArgumentException("Password is empty ");
        if (changePassword.getNewPassword().isEmpty())
            throw new IllegalArgumentException("Password is empty");
        return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(changePassword));
    }

    @GetMapping("/search/all")
    public ResponseEntity<List<UserDTO>> getAllUser(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<UserDTO> findAllUser = userService.findAll(pageable);
        if (findAllUser.isEmpty())
            throw new IllegalArgumentException("Dont have any user in the data base !!!");
        return ResponseEntity.status(HttpStatus.OK).body(findAllUser.getContent());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") Long id) {
        return userService.findOne(id).map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseThrow(
                () -> new IllegalArgumentException("Cant not find the User have id : " + id)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long id) {
        if (!userRepository.findById(id).isPresent())
            throw new IllegalArgumentException("Cant not find the user have Id :" + id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
