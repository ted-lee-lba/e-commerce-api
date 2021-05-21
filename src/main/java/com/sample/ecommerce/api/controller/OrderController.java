package com.sample.ecommerce.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.api.model.OrderProductResponse;
import com.sample.ecommerce.api.model.OrderRequest;
import com.sample.ecommerce.api.model.OrderResponse;
import com.sample.ecommerce.domain.dto.AddOrderDTO;
import com.sample.ecommerce.domain.dto.OrderDTO;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;
import com.sample.ecommerce.domain.service.OrderService;
import com.sample.ecommerce.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
public class OrderController {
    @Autowired
    private OrderService _orderService;

    @Autowired
    private GlobalModelMapper _globalModelMapper;
    
    @Secured({"ROLE_USER"})
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<OrderResponse>> get(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<OrderDTO> orderDtos = _orderService.getAll(userPrincipal.getId());
        return ResponseEntity.ok(mapToModel(orderDtos));
    }
    
    @Secured({"ROLE_USER"})
    @PostMapping
    @ResponseBody
    public ResponseEntity<List<OrderResponse>> add(@RequestBody OrderRequest model, Authentication authentication) throws ElementNotFoundException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        model.setBuyerId(userPrincipal.getId());

        AddOrderDTO addOrderDTO = mapToDTO(model);
        List<OrderDTO> orderDtos = _orderService.add(addOrderDTO, userPrincipal.getUsername());
        return ResponseEntity.ok(mapToModel(orderDtos));
    }

    private List<OrderResponse> mapToModel(List<OrderDTO> orderDtos) {
        return orderDtos.stream().map(c -> {
            OrderResponse orderResponse = _globalModelMapper.getMapper().map(c, OrderResponse.class);
            orderResponse.setProducts(
                c.getProducts().stream().map(d -> {
                    return _globalModelMapper.getMapper().map(d, OrderProductResponse.class);
                }).collect(Collectors.toList())
            );
            return orderResponse;
        }).collect(Collectors.toList());
    }

    private AddOrderDTO mapToDTO(OrderRequest orderRequest) {
        AddOrderDTO addOrderDto = _globalModelMapper.getMapper().map(orderRequest, AddOrderDTO.class);
        return addOrderDto;
    }
}
