package com.sample.ecommerce.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.data.mysql.repository.AccountRepository;
import com.sample.ecommerce.domain.dto.AccountDTO;
import com.sample.ecommerce.domain.exception.ElementExistedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MerchantService extends AccountService {

    @Autowired
    public MerchantService(GlobalModelMapper dtoMapper, AccountRepository accountRepository, 
        PasswordEncoder passwordEncoder) {
        super(dtoMapper, accountRepository, passwordEncoder);
    }
    
    @Transactional
    @Override
    public AccountDTO addNew(AccountDTO accountDto) throws ElementExistedException {
        accountDto.setAccountType("ROLE_MERCHANT");
        return super.addNew(accountDto);
    }

    public List<AccountDTO> getAll() {
        return super.getAll("ROLE_MERCHANT");
    }
}
