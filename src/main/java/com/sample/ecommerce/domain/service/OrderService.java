package com.sample.ecommerce.domain.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.data.mysql.entity.Order;
import com.sample.ecommerce.data.mysql.entity.OrderProduct;
import com.sample.ecommerce.data.mysql.entity.Product;
import com.sample.ecommerce.data.mysql.repository.OrderProductRepository;
import com.sample.ecommerce.data.mysql.repository.OrderRepository;
import com.sample.ecommerce.data.mysql.repository.ProductRepository;
import com.sample.ecommerce.domain.dto.AddOrderDTO;
import com.sample.ecommerce.domain.dto.AddOrderProductDTO;
import com.sample.ecommerce.domain.dto.OrderDTO;
import com.sample.ecommerce.domain.dto.OrderProductDTO;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;
import com.sample.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private GlobalModelMapper _dtoMapper;
    
    @Autowired
    private ProductRepository _productRepository;
    
    @Autowired
    private OrderRepository _orderRepository;
    
    @Autowired
    private OrderProductRepository _orderProductRepository;

    public List<OrderDTO> getAll(Integer buyerId) {
        List<Order> orders = _orderRepository.findByBuyerId(buyerId);
        return mapToDTO(orders);
    }
    
    public List<OrderDTO> add(AddOrderDTO addOrderDto, String actUser) throws ElementNotFoundException {
        Timestamp tsNow = DateUtils.currentTimeStamp();
        List<Order> orders = new ArrayList<>();
        for(AddOrderProductDTO addOrderProductDto : addOrderDto.getProducts()) {
            Product product = _productRepository.findById(addOrderProductDto.getProductId()).orElseThrow(
                () -> new ElementNotFoundException("Product id [" + addOrderProductDto.getProductId() + "] not found.")
            );

            OrderProduct orderProduct = OrderProduct.builder()
                .productName(product.getProductName())
                .productCode(product.getProductCode())
                .price(product.getPrice())
                .quantity(addOrderProductDto.getQuantity())
                .build();

            Optional<Order> foundOrder = orders.stream().filter(c -> {
                return c.getMerchantId().equals(product.getAccountId());
            }).findFirst();
            if (!foundOrder.isPresent()) {
                foundOrder = Optional.of(Order.builder()
                    .buyerId(addOrderDto.getBuyerId())
                    .merchantId(product.getAccountId())
                    .orderTime(tsNow)
                    .orderStatus("ACTIVE")
                    .createdBy(actUser)
                    .createdDate(tsNow)
                    .updatedBy(actUser)
                    .updatedDate(tsNow)
                    .build());
                orders.add(foundOrder.get());
            };
            foundOrder.get().getProducts().add(orderProduct);
        }
        return add(orders);
    }

    @Transactional
    private List<OrderDTO> add(List<Order> orders) {
        for(Order order : orders) {
            _orderRepository.save(order);
            
            for(OrderProduct orderProduct: order.getProducts()) {
                orderProduct.setOrder(order);
                _orderProductRepository.save(orderProduct);
            }
        }
        return mapToDTO(orders);
    }

    private List<OrderDTO> mapToDTO(List<Order> orders) {
        return orders.stream().map(c -> {
            OrderDTO orderDTO = _dtoMapper.getMapper().map(c, OrderDTO.class);
            orderDTO.setProducts(
                c.getProducts().stream().map(d -> {
                    return _dtoMapper.getMapper().map(d, OrderProductDTO.class);
                }).collect(Collectors.toList())
            );
            return orderDTO;
        }).collect(Collectors.toList());
    }
}
