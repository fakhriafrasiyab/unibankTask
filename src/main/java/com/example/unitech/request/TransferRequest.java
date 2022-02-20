package com.example.unitech.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    Integer userId;
    BigInteger transferAmount;
    List<Integer> accountIds;
}
