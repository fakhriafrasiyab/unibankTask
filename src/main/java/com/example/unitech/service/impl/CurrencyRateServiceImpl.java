package com.example.unitech.service.impl;

import com.example.unitech.entity.Currency;
import com.example.unitech.exception.CurrencyNotFoundException;
import com.example.unitech.repository.CurrencyRepository;
import com.example.unitech.service.CurrencyRateService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {
    private final CurrencyRepository currencyRepository;
    private boolean alreadyExecuted = false;

    public CurrencyRateServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;

    }

    //every time except the first time, when this service is requested it gets data from the cache
    //it does optimal solution for performance and cost
    @Override
    @Cacheable(value = "currency", key = "#currencyRate")
    public Currency getCurrencyByRate(String currencyRate) {
        mockCurrencies();
        Optional<Currency> currency = currencyRepository.findByCurrencyRate(currencyRate);
        if (currency.isPresent()) {
            return currency.get();
        } else {
            throw new CurrencyNotFoundException("This currency rate doesn't exist: " + currencyRate);
        }
    }

    private void mockCurrencies() {
        if (!alreadyExecuted) {
            Currency currency1 = new Currency("USD/AZN", "1.7");
            Currency currency2 = new Currency("AZN/USD", "0.59");
            Currency currency3 = new Currency("AZN/TL", "8");
            Currency currency4 = new Currency("TL/AZN", "0.12");
            Currency currency5 = new Currency("USD/TL", "13.65");
            Currency currency6 = new Currency("TL/USD", "0.073");
            List<Currency> currencies = new ArrayList<>();
            currencies.add(currency1);
            currencies.add(currency2);
            currencies.add(currency3);
            currencies.add(currency4);
            currencies.add(currency5);
            currencies.add(currency6);
            currencyRepository.saveAll(currencies);
            alreadyExecuted = true;
        }

    }


}
