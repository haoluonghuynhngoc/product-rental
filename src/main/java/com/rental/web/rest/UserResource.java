package com.rental.web.rest;
import com.rental.domain.enums.UserStatus;
import com.rental.repository.UserRepository;
import com.rental.service.UserService;
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


@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO users) {
        if (users.getUsername().trim().length() < 5)
            throw new IllegalArgumentException("Tên người dùng phải lớn hơn 5 ");
        if (users.getPassword().trim().length() < 5)
            throw new IllegalArgumentException("Mật khẩu người dùng phải lớn hơn 5 ");
        if (userRepository.findByUsername(users.getUsername()) != null)
            throw new IllegalArgumentException("Tên người dùng đã tồn tại ");
        if (userRepository.findByEmail(users.getEmail()) != null)
            throw new IllegalArgumentException("Email người dùng đã tồn tại ");
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(users));

    }

    @PostMapping("/login") // login loi
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO users) {
        if (users.getStatus() == UserStatus.LOCKED)
            throw new IllegalArgumentException("Tài khoản của bạn đã bị khóa");
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(users));
    }


    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        if (!userRepository.findById(userDTO.getId()).isPresent())
            throw new IllegalArgumentException("Không thể tìm thấy người dùng");
        return userService.updateUser(userDTO).map(userData -> ResponseEntity.status(HttpStatus.OK).body(userData)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping("/change/password")
    public ResponseEntity<?> changeUserPassword(@RequestBody PasswordChangeDTO changePassword) {
        if (userRepository.findByUsername(changePassword.getUserName()) == null)
            throw new IllegalArgumentException("Tên người dùng không tồn tại");
        return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(changePassword));
    }

    @GetMapping("/search/all")
    public ResponseEntity<List<UserDTO>> getAllUser(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<UserDTO> findAllUser = userService.findAll(pageable);
        if (findAllUser.isEmpty())
            throw new IllegalArgumentException("Không thể tìm thấy bất kì người dùng trong dữ liệu");
        return ResponseEntity.status(HttpStatus.OK).body(findAllUser.getContent());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") Long id) {
        return userService.findOne(id).map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseThrow(
                () -> new IllegalArgumentException("Không thể tìm thấy người dùng có id : " + id)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable(value = "id") Long id) {
        if (!userRepository.findById(id).isPresent())
            throw new IllegalArgumentException("Không thể tìm thấy người dùng có id :" + id);
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
