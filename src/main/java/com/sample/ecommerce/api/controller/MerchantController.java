package com.sample.ecommerce.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.api.model.AccountRequest;
import com.sample.ecommerce.api.model.AccountResponse;
import com.sample.ecommerce.api.model.ExceptionResponse;
import com.sample.ecommerce.api.model.ProductRequest;
import com.sample.ecommerce.api.model.ProductResponse;
import com.sample.ecommerce.domain.dto.AccountDTO;
import com.sample.ecommerce.domain.dto.ProductDTO;
import com.sample.ecommerce.domain.exception.ElementExistedException;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;
import com.sample.ecommerce.domain.service.MerchantService;
import com.sample.ecommerce.domain.service.ProductService;
import com.sample.ecommerce.security.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
@RequestMapping("api/merchant")
public class MerchantController {
    private final Logger logger = LoggerFactory.getLogger(MerchantController.class);
    
    @Autowired
    private MerchantService _merchantService;
    
    @Autowired
    private ProductService _productService;

    @Autowired
    private GlobalModelMapper _globalModelMapper;

    @Secured({ "ROLE_ADMIN" })
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AccountResponse>> get() {
        List<AccountResponse> result = _merchantService.getAll().stream().map(c -> {
            return _globalModelMapper.getMapper().map(c, AccountResponse.class);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Secured({ "ROLE_ADMIN" })
    @PostMapping
    @ResponseBody
    public ResponseEntity<AccountResponse> add(@RequestBody AccountRequest model) throws ElementExistedException {
        AccountDTO accountDto = _globalModelMapper.getMapper().map(model, AccountDTO.class);
        accountDto = _merchantService.addNew(accountDto);
        return ResponseEntity.ok(_globalModelMapper.getMapper().map(accountDto, AccountResponse.class));
    }

    @PutMapping("confirm-account/{username}")
    @ResponseBody
    public ResponseEntity<AccountResponse> confirmAccount(@PathVariable String userName) {
        _merchantService.confirmation(userName);
        return ResponseEntity.ok(AccountResponse.builder().userName(userName).build());
    }

    @PutMapping("suspend/{username}")
    @Secured({ "ROLE_ADMIN" })
    @ResponseBody
    public ResponseEntity<AccountResponse> suspendAccount(@PathVariable String userName) throws ElementNotFoundException {
        _merchantService.suspend(userName);
        return ResponseEntity.ok(AccountResponse.builder().userName(userName).build());
    }

    @Secured({ "ROLE_MERCHANT" })
    @GetMapping("products")
    @ResponseBody
    public ResponseEntity<List<AccountResponse>> get(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<AccountResponse> result = _productService.getAll(userPrincipal.getId()).stream().map(c -> {
            return _globalModelMapper.getMapper().map(c, AccountResponse.class);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Secured({ "ROLE_MERCHANT" })
    @PostMapping("product")
    @ResponseBody
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest model, Authentication authentication) throws ElementExistedException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        ProductDTO product = _globalModelMapper.getMapper().map(model, ProductDTO.class);
        product.setAccountId(userPrincipal.getId());
        product = _productService.addNew(product, userPrincipal.getUsername());
        return ResponseEntity.ok(_globalModelMapper.getMapper().map(product, ProductResponse.class));
    }

    @Secured({ "ROLE_MERCHANT" })
    @PutMapping("product/{productId}")
    @ResponseBody
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductRequest model, Authentication authentication) throws ElementExistedException, ElementNotFoundException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        ProductDTO product = _globalModelMapper.getMapper().map(model, ProductDTO.class);
        product.setAccountId(userPrincipal.getId());
        product.setProductId(productId);
        product = _productService.update(product, userPrincipal.getUsername());
        return ResponseEntity.ok(_globalModelMapper.getMapper().map(product, ProductResponse.class));
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