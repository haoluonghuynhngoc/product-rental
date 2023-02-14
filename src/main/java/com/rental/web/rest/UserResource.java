package com.rental.web.rest;

import com.rental.service.dto.AccountCreateDTO;
import com.rental.service.dto.AccountInfoDTO;
import com.rental.service.dto.AccountDTO;
import com.rental.service.dto.AccountPasswordDTO;
import com.rental.web.rest.restponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.service.AccountService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    private AccountService accountService;
<<<<<<< HEAD

    @PostMapping("/create") // có thời gian thì thêm check điều kiện  gmail và phone
    public ResponseEntity<ResponseObject> createUser(@RequestBody AccountCreateDTO accountCreateDTO) {
        AccountDTO checkUsers = accountService.createUser(accountCreateDTO);
        return checkUsers == null ? ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because the userName : " + accountCreateDTO.getUserName() + " in exit", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Create the user Successfully", "")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@RequestBody AccountCreateDTO accountCreateDTO) {
        AccountDTO checkUser = accountService.loginUsers(accountCreateDTO);
        return checkUser == null ? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ResponseObject("FAIL", "Fail !!! Because incorrect userName or password ", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Login Successfully", checkUser)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable(value = "id") Long id, @RequestBody AccountInfoDTO accountInfoDTO) {
        Optional<AccountInfoDTO> infoUser = accountService.updateUser(accountInfoDTO, id);
        return infoUser.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Update Users Successfully", infoUser)
        ) : ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because cant not found the user have id : " + id + " in the table ", "")
        );
    }

    @PostMapping("/change/password")
    public ResponseEntity<ResponseObject> changeUserPassword(@RequestBody AccountPasswordDTO changePassword) {
        accountService.changePassword(changePassword);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Change password have Users :" + changePassword.getUserName() + " Successfully", "")
        );
    }
=======
>>>>>>> d366af6133a04a2e3466686493bfced099525e4c

    @PostMapping("/create") // có thời gian thì thêm check điều kiện  gmail và phone
    public ResponseEntity<ResponseObject> createUser(@RequestBody AccountCreateDTO accountCreateDTO) {
        AccountDTO checkUsers = accountService.createUser(accountCreateDTO);
        return checkUsers == null ? ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because the userName : " + accountCreateDTO.getUserName() + " in exit", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Create the user Successfully", "")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@RequestBody AccountCreateDTO accountCreateDTO) {
        AccountDTO checkUser = accountService.loginUsers(accountCreateDTO);
        return checkUser == null ? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ResponseObject("FAIL", "Fail !!! Because incorrect userName or password ", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Login Successfully", checkUser)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable(value = "id") Long id, @RequestBody AccountInfoDTO accountInfoDTO) {
        Optional<AccountInfoDTO> infoUser = accountService.updateUser(accountInfoDTO, id);
        return infoUser.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Update Users Successfully", infoUser)
        ) : ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because cant not found the user have id : " + id + " in the table ", "")
        );
    }

    @PostMapping("/change/password")
    public ResponseEntity<ResponseObject> changeUserPassword(@RequestBody AccountPasswordDTO changePassword) {
        accountService.changePassword(changePassword);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Change password have Users :" + changePassword.getUserName() + " Successfully", "")
        );
    }


}
