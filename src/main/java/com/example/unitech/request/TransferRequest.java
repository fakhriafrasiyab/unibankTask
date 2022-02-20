package com.example.unitech.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {
    Integer userId;
    BigInteger transferAmount;
    List<Integer> accountIds;
}
