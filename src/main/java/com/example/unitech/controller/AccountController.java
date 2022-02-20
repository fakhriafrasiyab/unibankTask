package com.example.unitech.controller;

import com.example.unitech.entity.Account;
import com.example.unitech.request.TransferRequest;
import com.example.unitech.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("{userId}")
    public List<Account> getActiveAccounts(@PathVariable Integer userId) {
        return accountService.getActiveAccount(userId);
    }

    @RequestMapping(value = "transfer", method = RequestMethod.POST)
    public void transferMoneyToOtherAccounts(@RequestBody TransferRequest transferRequest) {
        accountService.transferMoneyToOtherAccounts(transferRequest);
    }
}
