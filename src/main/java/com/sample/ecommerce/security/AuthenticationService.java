package com.sample.ecommerce.security;

import com.sample.ecommerce.data.mysql.entity.Account;
import com.sample.ecommerce.data.mysql.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AuthenticationService")
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository _accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        Account user = _accountRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) throws Exception {
        Account user = _accountRepository.findById(id).orElseThrow(
            () -> new Exception("User id [" + id.toString() + "] not found")
        );

        return UserPrincipal.create(user);
    }
}
