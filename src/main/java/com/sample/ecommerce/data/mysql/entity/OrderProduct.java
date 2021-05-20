package com.sample.ecommerce.data.mysql.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "com.sample.ecommerce.data.mysql.entity.OrderProduct")
@Table(name = "order_products")
public class OrderProduct implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_product_id", nullable = false)
  private Integer order_product_id;
  @Column(name = "order_id", nullable = false)
  private Integer orderId;
  @Column(name = "product_code", nullable = false)
  private String productCode;
  @Column(name = "order_time", nullable = false)
  private String productDescr;
  @Column(name = "price", nullable = false)
  private BigDecimal price;
  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}