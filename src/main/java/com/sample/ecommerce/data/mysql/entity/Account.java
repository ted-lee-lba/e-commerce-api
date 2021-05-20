package com.sample.ecommerce.data.mysql.entity;

import java.io.Serializable;
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
@Entity(name = "com.sample.ecommerce.data.mysql.entity.Account")
@Table(name = "accounts")
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id", nullable = false)
  private Integer accountId;
  @Column(name = "username", nullable = false)
  private String userName;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "account_type", nullable = false)
  private String accountType;
  @Column(name = "is_email_verified", nullable = false)
  private Boolean emailVerified;
  @Column(name = "status", nullable = false)
  private String status;
  @Column(name = "created_date", nullable = false)
  private Timestamp createdDate;
  @Column(name = "created_by", nullable = false)
  private String createdBy;
  @Column(name = "updated_date", nullable = false)
  private Timestamp updatedDate;
  @Column(name = "updated_by", nullable = false)
  private String updatedBy;
}