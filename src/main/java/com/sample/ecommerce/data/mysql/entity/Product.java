package com.sample.ecommerce.data.mysql.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
@Entity(name = "com.sample.ecommerce.data.mysql.entity.Product")
@Table(name = "products")
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id", nullable = false)
  private Integer productId;
  @Column(name = "account_id", nullable = false)
  private Integer accountId;
  @Column(name = "product_code", nullable = false)
  private String productCode;
  @Column(name = "product_name", nullable = false)
  private String productName;
  @Column(name = "product_descr", nullable = false)
  private String productDescr;
  @Column(name = "price", nullable = false)
  private BigDecimal price;
  @Column(name = "created_date", nullable = false)
  private Timestamp createdDate;
  @Column(name = "created_by", nullable = false)
  private String createdBy;
  @Column(name = "updated_date", nullable = false)
  private Timestamp updatedDate;
  @Column(name = "updated_by", nullable = false)
  private String updatedBy;
}