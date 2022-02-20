package com.example.unitech.repository;

import com.example.unitech.entity.Account;
import com.example.unitech.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findAllByUserIdAndAccountStatus(Integer userId, AccountStatus status);

    List<Account> findAllByUserId(Integer userId);

    List<Account> findAllByIdIn(List<Integer> accountIds);
}
