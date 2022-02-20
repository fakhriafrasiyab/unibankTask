package com.example.unitech.service;

import com.example.unitech.entity.Currency;

public interface CurrencyRateService {
    Currency getCurrencyByRate(String currencyRate);
}
