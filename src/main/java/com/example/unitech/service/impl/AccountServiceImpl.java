package com.example.unitech.service.impl;

import com.example.unitech.entity.Account;
import com.example.unitech.entity.enums.AccountStatus;
import com.example.unitech.exception.AccountNotFoundException;
import com.example.unitech.exception.AccountStatusInactiveException;
import com.example.unitech.exception.NotEnoughBalance;
import com.example.unitech.exception.SameAccountException;
import com.example.unitech.repository.AccountRepository;
import com.example.unitech.request.TransferRequest;
import com.example.unitech.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getActiveAccount(Integer userId) {
        return accountRepository.findAllByUserIdAndAccountStatus(userId, AccountStatus.ACTIVE);
    }

    public void transferMoneyToOtherAccounts(TransferRequest transferRequest) {
        List<Account> myAccounts = accountRepository.findAllByUserId(transferRequest.getUserId());
        List<Account> targetAccounts = accountRepository.findAllByIdIn(transferRequest.getAccountIds());
        if (targetAccounts.isEmpty() || myAccounts.isEmpty())
            throw new AccountNotFoundException("There is not this kind of accounts");
        for (Account account : myAccounts) {
            if (account.getAccountStatus() == AccountStatus.INACTIVE)
                throw new AccountStatusInactiveException("Only can transfer happen from active accounts");
            if (account.getBalance().subtract(transferRequest.getTransferAmount()).compareTo(BigInteger.ZERO) < 0)
                throw new NotEnoughBalance("Transfer amount is higher than balance");
            account.setBalance(account.getBalance().subtract(transferRequest.getTransferAmount()));
            accountRepository.save(account);
            for (Account targetAccount : targetAccounts) {
                if (targetAccount.equals(account))
                    throw new SameAccountException("Cannot transfer to same account");
                if (targetAccount.getAccountStatus() == AccountStatus.INACTIVE)
                    throw new AccountStatusInactiveException("Only can transfer to active accounts");
                targetAccount.setBalance(targetAccount.getBalance().add(transferRequest.getTransferAmount()));
                accountRepository.save(targetAccount);
            }
        }
    }
}
