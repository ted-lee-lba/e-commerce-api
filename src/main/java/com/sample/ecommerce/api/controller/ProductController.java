package com.sample.ecommerce.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.api.model.AccountResponse;
import com.sample.ecommerce.domain.service.ProductService;
import com.sample.ecommerce.security.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService _productService;

    @Autowired
    private GlobalModelMapper _globalModelMapper;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<AccountResponse>> get(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<AccountResponse> result = _productService.getAll(userPrincipal.getId()).stream().map(c -> {
            return _globalModelMapper.getMapper().map(c, AccountResponse.class);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
