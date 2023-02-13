package com.rental.web.rest;

import com.rental.service.dto.UserInfoDTO;
import com.rental.service.dto.UserDTO;
import com.rental.service.dto.UserPasswordDTO;
import com.rental.service.dto.VoucherDTO;
import com.rental.web.rest.restponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    private UserService userService;

    @PostMapping("/create") // có thời gian thì thêm check điều kiện  gmail và phone
    public ResponseEntity<ResponseObject> createUser(@RequestBody UserDTO userDTO) {
        UserDTO checkUsers = userService.create(userDTO);
        return checkUsers == null ? ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because the userName : " + userDTO.getUserName() + " in exit", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Create the user Successfully", "")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@RequestBody UserDTO userDTO) {
        UserDTO checkUser = userService.loginUsers(userDTO);
        return checkUser == null ? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ResponseObject("FAIL", "Fail !!! Because incorrect userName or password ", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Login Successfully", checkUser)
        );
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ResponseObject> removeUser(@PathVariable(value = "id") Long id) {
        boolean check = userService.removeUser(id);
        return check ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Remove Users Successfully", "")
        ) : ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because cant not found the user have id : " + id + " in the table ", "")
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserInfoDTO infoUserDTO) {
        Optional<UserInfoDTO> infoUser = userService.updateUser(infoUserDTO, id);
        return infoUser.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Update Users Successfully", infoUser)
        ) : ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because cant not found the user have id : " + id + " in the table ", "")
        );
    }

    @PostMapping("/change/password")
    public ResponseEntity<ResponseObject> changeUserPassword(@RequestBody UserPasswordDTO changePassword) {
        userService.changePassword(changePassword);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Change password have Users :" + changePassword.getUserName() + " Successfully", "")
        );
    }
    @GetMapping("/getListUser")
    public ResponseEntity<ResponseObject> getListUser(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<UserDTO> page = userService.findAll(pageable);
        return !page.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Update Users Successfully", page.getContent())
        ) : ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because Size or Page element too large ", "")
        );
    }

}
