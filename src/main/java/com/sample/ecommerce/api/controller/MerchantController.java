package com.sample.ecommerce.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.api.model.AccountRequest;
import com.sample.ecommerce.api.model.AccountResponse;
import com.sample.ecommerce.api.model.ProductRequest;
import com.sample.ecommerce.api.model.ProductResponse;
import com.sample.ecommerce.domain.dto.AccountDTO;
import com.sample.ecommerce.domain.dto.ProductDTO;
import com.sample.ecommerce.domain.exception.ElementExistedException;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;
import com.sample.ecommerce.domain.service.AccountService;
import com.sample.ecommerce.domain.service.ProductService;
import com.sample.ecommerce.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
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
    
    @Autowired
    private AccountService _accountService;
    
    @Autowired
    private ProductService _productService;

    @Autowired
    private GlobalModelMapper _globalModelMapper;

    @PostMapping("register")
    @ResponseBody
    public ResponseEntity<AccountResponse> add(@RequestBody AccountRequest model) throws ElementExistedException {
        AccountDTO accountDto = _globalModelMapper.getMapper().map(model, AccountDTO.class);
        accountDto = _accountService.addNewMerchant(accountDto);
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

    @Secured({ "ROLE_MERCHANT" })
    @GetMapping("products")
    @ResponseBody
    public ResponseEntity<List<ProductResponse>> get(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<ProductResponse> result = _productService.getAll(userPrincipal.getId()).stream().map(c -> {
            return _globalModelMapper.getMapper().map(c, ProductResponse.class);
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
}
