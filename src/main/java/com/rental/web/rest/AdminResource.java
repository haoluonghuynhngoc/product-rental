package com.rental.web.rest;

import com.rental.service.AccountService;
import com.rental.service.dto.AccountCreateDTO;
import com.rental.service.dto.AccountDTO;
import com.rental.service.dto.AccountInfoDTO;
import com.rental.service.dto.AccountPasswordDTO;
import com.rental.web.rest.restponse.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminResource {
    @Autowired
    private AccountService accountService;
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createUser(@RequestBody AccountCreateDTO accountCreateDTO) {
        AccountDTO checkUsers = accountService.createAdmin(accountCreateDTO);
        return checkUsers == null ? ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because the userName : " + accountCreateDTO.getUserName() + " in exit", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Create the user Successfully", "")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@RequestBody AccountCreateDTO AccountCreateDTO) {
        AccountDTO checkAccount = accountService.loginUsers(AccountCreateDTO);
        return checkAccount == null ? ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                new ResponseObject("FAIL", "Fail !!! Because incorrect userName or password ", "")
        ) : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Login Successfully", checkAccount)
        );
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ResponseObject> removeUser(@PathVariable(value = "id") Long id) {
        boolean check = accountService.removeUser(id);
        return check ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Remove Users Successfully", "")
        ) : ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because cant not found the user have id : " + id + " in the table ", "")
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
    @GetMapping("/getListUser")
    public ResponseEntity<ResponseObject> getListUser(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<AccountDTO> page = accountService.findAll(pageable);
        return !page.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("SUCCESS", "Update Users Successfully", page.getContent())
        ) : ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ResponseObject("FAIL", "Fail !!! Because Size or Page element too large ", "")
        );
    }
}
