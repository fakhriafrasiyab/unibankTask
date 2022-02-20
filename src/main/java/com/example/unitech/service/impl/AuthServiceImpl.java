package com.example.unitech.service.impl;

import com.example.unitech.entity.Account;
import com.example.unitech.entity.User;
import com.example.unitech.entity.enums.AccountStatus;
import com.example.unitech.exception.PinAlreadyExsistException;
import com.example.unitech.repository.AccountRepository;
import com.example.unitech.repository.UserRepository;
import com.example.unitech.request.LoginRequest;
import com.example.unitech.request.SignUpRequest;
import com.example.unitech.response.JwtResponse;
import com.example.unitech.security.jwt.JwtUtils;
import com.example.unitech.security.services.UserDetailsImpl;
import com.example.unitech.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                           AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.accountRepository = accountRepository;
    }

    public void registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByPin(signUpRequest.getPin())) {
            throw new PinAlreadyExsistException();
        }

        // Create new user's account
        User user = new User(signUpRequest.getPin(), passwordEncoder.encode(signUpRequest.getPassword()));
        user.setAccounts(addRandomAccounts(user, BigInteger.valueOf(1000)));
        userRepository.save(user);
    }

    @Override
    public JwtResponse authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getPin(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername());
    }

    private List<Account> addRandomAccounts(User user, BigInteger balance) {
        Account account1 = new Account(AccountStatus.ACTIVE, balance);
        Account account2 = new Account(AccountStatus.ACTIVE, balance);
        Account account3 = new Account(AccountStatus.ACTIVE, balance);
        Account account4 = new Account(AccountStatus.ACTIVE, balance);
        Account account5 = new Account(AccountStatus.ACTIVE, balance);
        account1.setUser(user);
        account2.setUser(user);
        account3.setUser(user);
        account4.setUser(user);
        account5.setUser(user);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        accounts.add(account5);
        accountRepository.saveAll(accounts);
        return accounts;
    }

}
