package com.sample.ecommerce.api.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
  private Integer accountId;
  @NotBlank
  private String userName;
  @NotBlank
  private String password;
}