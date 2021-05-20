package com.sample.ecommerce.domain.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.data.mysql.entity.Product;
import com.sample.ecommerce.data.mysql.repository.ProductRepository;
import com.sample.ecommerce.domain.dto.ProductDTO;
import com.sample.ecommerce.domain.exception.ElementExistedException;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;
import com.sample.utils.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductService {
    @Autowired
    private GlobalModelMapper _dtoMapper;
    
    @Autowired
    private ProductRepository _productRepository;
    
    public List<ProductDTO> getAll() {
        return _productRepository.findAll().stream().map(c -> {
            return _dtoMapper.getMapper().map(c, ProductDTO.class);
        }).collect(Collectors.toList());
    }
    
    public List<ProductDTO> getAll(Integer accountId) {
        return _productRepository.findByAccountId(accountId).stream().map(c -> {
            return _dtoMapper.getMapper().map(c, ProductDTO.class);
        }).collect(Collectors.toList());
    }

    public ProductDTO addNew(ProductDTO productDto, String actUser) throws ElementExistedException {
        if (_productRepository.findByProductCode(productDto.getProductCode()).isPresent()) {
            throw new ElementExistedException("Product code [" + productDto.getProductCode() + "] already existed.");
        }

        Timestamp tsNow = DateUtils.currentTimeStamp();
        Product product = _dtoMapper.getMapper().map(productDto, Product.class);
        product.setCreatedBy(actUser);
        product.setCreatedDate(tsNow);
        product.setUpdatedBy(actUser);
        product.setUpdatedDate(tsNow);
        _productRepository.save(product);

        return _dtoMapper.getMapper().map(product, ProductDTO.class);
    }

    public ProductDTO update(ProductDTO productDto, String actUser) throws ElementNotFoundException {
        if (_productRepository.findByProductIdAndAccountId(productDto.getProductId(), productDto.getAccountId()).isPresent()) {
            throw new ElementNotFoundException("Product code [" + productDto.getProductCode() + "] not found.");
        }

        Timestamp tsNow = DateUtils.currentTimeStamp();
        Product product = _dtoMapper.getMapper().map(productDto, Product.class);
        product.setUpdatedBy(actUser);
        product.setUpdatedDate(tsNow);
        _productRepository.save(product);

        return _dtoMapper.getMapper().map(product, ProductDTO.class);

    }
}
