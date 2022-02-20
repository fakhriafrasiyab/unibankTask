package com.example.unitech.service;

import com.example.unitech.entity.Account;
import com.example.unitech.request.TransferRequest;

import java.util.List;

public interface AccountService {
    List<Account> getActiveAccount(Integer userId);

    void transferMoneyToOtherAccounts(TransferRequest transferRequest);
}
