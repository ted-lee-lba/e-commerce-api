package com.sample.ecommerce.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
  private Integer accountId;
  private String userName;
  private String password;
  private String accountType;
  private Boolean isEmailVerified;
  private String status;
}