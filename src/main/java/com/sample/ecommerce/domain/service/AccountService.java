package com.sample.ecommerce.domain.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.sample.ecommerce.GlobalModelMapper;
import com.sample.ecommerce.data.mysql.entity.Account;
import com.sample.ecommerce.data.mysql.repository.AccountRepository;
import com.sample.ecommerce.domain.dto.AccountDTO;
import com.sample.ecommerce.domain.exception.ElementExistedException;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;
import com.sample.utils.DateUtils;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountService {
    protected GlobalModelMapper _dtoMapper;
    protected AccountRepository _accountRepository;
    protected PasswordEncoder _passwordEncoder;

    protected AccountService(GlobalModelMapper dtoMapper, AccountRepository accountRepository, 
        PasswordEncoder passwordEncoder) {
        _dtoMapper = dtoMapper;
        _accountRepository = accountRepository;
        _passwordEncoder = passwordEncoder;
    }
    
    public List<AccountDTO> getAll(String accountType) {
        return _accountRepository.findByAccountType(accountType).stream().map(c -> {
            return _dtoMapper.getMapper().map(c, AccountDTO.class);
        }).collect(Collectors.toList());
    }
    
    public List<AccountDTO> getAll() {
        return _accountRepository.findAll().stream().map(c -> {
            return _dtoMapper.getMapper().map(c, AccountDTO.class);
        }).collect(Collectors.toList());
    }

    @Transactional
    public AccountDTO addNewMerchant(AccountDTO accountDto) throws ElementExistedException {
        accountDto.setAccountType("ROLE_MERCHANT");
        return addNew(accountDto);
    }

    @Transactional
    public AccountDTO addNewUser(AccountDTO accountDto) throws ElementExistedException {
        accountDto.setAccountType("ROLE_USER");
        return addNew(accountDto);
    }

    @Transactional
    public AccountDTO addNew(AccountDTO accountDto) throws ElementExistedException {
        if (_accountRepository.findByUserName(accountDto.getUserName()).isPresent()) {
            throw new ElementExistedException("User name [" + accountDto.getUserName() + "] already existed.");
        }

        Timestamp tsNow = DateUtils.currentTimeStamp();
        Account account = _dtoMapper.getMapper().map(accountDto, Account.class);
        account.setEmailVerified(false);
        account.setStatus("ACTIVE");
        account.setPassword(_passwordEncoder.encode(accountDto.getPassword()));
        account.setCreatedBy(account.getUserName());
        account.setCreatedDate(tsNow);
        account.setUpdatedBy(account.getUserName());
        account.setUpdatedDate(tsNow);
        _accountRepository.save(account);

        return _dtoMapper.getMapper().map(account, AccountDTO.class);
    }

    @Transactional
    public void confirmation(String userName) {
        Optional<Account> account = _accountRepository.findByUserName(userName);
        if (!account.isPresent()) {
            return;
        }

        Timestamp tsNow = DateUtils.currentTimeStamp();
        account.get().setEmailVerified(true);
        account.get().setUpdatedBy(account.get().getUserName());
        account.get().setUpdatedDate(tsNow);
        _accountRepository.save(account.get());
    }

    @Transactional
    public void suspend(String userName) throws ElementNotFoundException {
        Optional<Account> account = _accountRepository.findByUserName(userName);
        if (!account.isPresent()) {
            throw new ElementNotFoundException("User name [" + userName + "] not found.");
        }

        Timestamp tsNow = DateUtils.currentTimeStamp();
        account.get().setStatus("SUSPEND");
        account.get().setUpdatedBy(account.get().getUserName());
        account.get().setUpdatedDate(tsNow);
        _accountRepository.save(account.get());
    }
}
