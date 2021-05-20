package com.sample.ecommerce.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Integer accountId;
    private String userName;
    private String accountType;
    private Boolean isEmailVerified;
}
