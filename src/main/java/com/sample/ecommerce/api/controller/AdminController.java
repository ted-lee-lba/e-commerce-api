package com.sample.ecommerce.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.api.model.AccountRequest;
import com.sample.ecommerce.api.model.AccountResponse;
import com.sample.ecommerce.api.model.ExceptionResponse;
import com.sample.ecommerce.domain.dto.AccountDTO;
import com.sample.ecommerce.domain.exception.ElementExistedException;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;
import com.sample.ecommerce.domain.service.AdminService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    
    @Autowired
    private AdminService _adminService;

    @Autowired
    private GlobalModelMapper _globalModelMapper;

    @Secured({ "ROLE_ADMIN" })
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AccountResponse>> get() {
        List<AccountResponse> result = _adminService.getAll().stream().map(c -> {
            return _globalModelMapper.getMapper().map(c, AccountResponse.class);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    @ResponseBody
    public ResponseEntity<AccountResponse> add(@RequestBody AccountRequest model) throws ElementExistedException {
        AccountDTO accountDto = _globalModelMapper.getMapper().map(model, AccountDTO.class);
        accountDto = _adminService.addNew(accountDto);
        return ResponseEntity.ok(_globalModelMapper.getMapper().map(accountDto, AccountResponse.class));
    }

    @PutMapping("confirm-account/{username}")
    @ResponseBody
    public ResponseEntity<AccountResponse> confirmAccount(@PathVariable String userName) {
        _adminService.confirmation(userName);
        return ResponseEntity.ok(AccountResponse.builder().userName(userName).build());
    }

    @PutMapping("suspend/{username}")
    @Secured({ "ROLE_ADMIN" })
    @ResponseBody
    public ResponseEntity<AccountResponse> suspendAccount(@PathVariable String userName) throws ElementNotFoundException {
        _adminService.suspend(userName);
        return ResponseEntity.ok(AccountResponse.builder().userName(userName).build());
    }

	@ExceptionHandler({ ElementNotFoundException.class })
	public ResponseEntity<ExceptionResponse> customHandleException(ElementNotFoundException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.NOT_FOUND.value()).build(), HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler({ ElementExistedException.class })
	public ResponseEntity<ExceptionResponse> customHandleException(ElementExistedException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ExceptionResponse> customHandleException(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
