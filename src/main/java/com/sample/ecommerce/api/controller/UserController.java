package com.sample.ecommerce.api.controller;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.api.model.AccountRequest;
import com.sample.ecommerce.api.model.AccountResponse;
import com.sample.ecommerce.domain.dto.AccountDTO;
import com.sample.ecommerce.domain.exception.ElementExistedException;
import com.sample.ecommerce.domain.service.AccountService;
import com.sample.ecommerce.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    
    @Autowired
    private AccountService _accountService;

    @Autowired
    private GlobalModelMapper _globalModelMapper;

    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<AccountResponse> add(@RequestBody AccountRequest model) throws ElementExistedException {
        AccountDTO accountDto = _globalModelMapper.getMapper().map(model, AccountDTO.class);
        accountDto = _accountService.addNewUser(accountDto);
        return ResponseEntity.ok(_globalModelMapper.getMapper().map(accountDto, AccountResponse.class));
    }

    @PutMapping("confirm-account/{userName}")
    @ResponseBody
    public ResponseEntity<AccountResponse> confirmAccount(@PathVariable("userName") String userName, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!userPrincipal.getUsername().equals(userName)) {
            throw new AccessDeniedException("Invalid resources");
        }
        _accountService.confirmation(userName);
        return ResponseEntity.ok(AccountResponse.builder().userName(userName).build());
    }
}
