package com.example.unitech.service;

import com.example.unitech.entity.Account;
import com.example.unitech.entity.User;
import com.example.unitech.entity.enums.AccountStatus;
import com.example.unitech.exception.SameAccountException;
import com.example.unitech.repository.AccountRepository;
import com.example.unitech.request.TransferRequest;
import com.example.unitech.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private static final Integer USER_ID = 14;
    private static final Integer ID = 10;
    private static final AccountStatus ACCOUNT_STATUS = AccountStatus.ACTIVE;
    private static final BigInteger BALANCE = BigInteger.valueOf(1000);
    private static final BigInteger TRANSFER_AMOUNT = BigInteger.valueOf(100);
    Account account;
    Account account2;
    User user;
    TransferRequest transferRequest;

    @InjectMocks
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;


    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(ID)
                .accountStatus(ACCOUNT_STATUS)
                .balance(BALANCE)
                .user(user)
                .build();
        account2 = Account.builder()
                .id(ID)
                .accountStatus(ACCOUNT_STATUS)
                .balance(BALANCE)
                .user(user)
                .build();
        transferRequest = TransferRequest.builder()
                .userId(USER_ID)
                .accountIds(List.of(ID))
                .transferAmount(TRANSFER_AMOUNT)
                .build();
    }

    @Test
    void getActiveAccountThenSuccess() {
        // arrange
        when(accountRepository.findAllByUserIdAndAccountStatus(anyInt(), any())).thenReturn(List.of(account));

        //act
        List<Account> activeAccounts = accountService.getActiveAccount(USER_ID);

        //assertion
        assertThat(activeAccounts).isNotNull();
        assertThat(activeAccounts).isEqualTo(List.of(account));
    }

    @Test
    void transferMoneyToOtherAccountsThenSameAccountException() {
        //arrange
        when(accountRepository.findAllByUserId(anyInt())).thenReturn(List.of(account));
        when(accountRepository.findAllByIdIn(anyList())).thenReturn(List.of(account));

        //act & assert
        assertThrows(SameAccountException.class, () ->
                accountService.transferMoneyToOtherAccounts(transferRequest));
    }

    @Test
    void transferMoneyToOtherAccountsThenSuccess() {
        //arrange
        when(accountRepository.findAllByUserId(anyInt())).thenReturn(List.of(account));
        when(accountRepository.findAllByIdIn(anyList())).thenReturn(List.of(account2));

        //act
        accountService.transferMoneyToOtherAccounts(transferRequest);

        //assertion
        verify(accountRepository,times(1)).findAllByUserId(anyInt());
        assertThat(account).isNotEqualTo(account2);
    }
}
