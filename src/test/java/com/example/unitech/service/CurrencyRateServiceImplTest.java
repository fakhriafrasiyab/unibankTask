package com.example.unitech.service;

import com.example.unitech.entity.Currency;
import com.example.unitech.exception.CurrencyNotFoundException;
import com.example.unitech.exception.SameAccountException;
import com.example.unitech.repository.CurrencyRepository;
import com.example.unitech.service.impl.CurrencyRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyRateServiceImplTest {

    private static final String CURRENCY_RATE = "1.7";
    private static final String NAME = "USD/AZN";
    Currency currency;

    @InjectMocks
    private CurrencyRateServiceImpl currencyRateService;

    @Mock
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        currency = new Currency(CURRENCY_RATE, NAME);
    }

    @Test
    void getCurrencyByRateThenSuccess() {
        //arrange
        when(currencyRepository.findByCurrencyRate(anyString())).thenReturn(Optional.of(currency));

        //act
        Currency currency = currencyRateService.getCurrencyByRate(CURRENCY_RATE);

        //assert
        assertThat(currency).isNotNull();
        assertThat(currency).isEqualTo(this.currency);
    }

    @Test
    void getCurrencyByRateThenCurrencyNotFoundException() {
        //arrange
        when(currencyRepository.findByCurrencyRate(anyString())).thenReturn(Optional.empty());

        //act & assert
        assertThrows(CurrencyNotFoundException.class, () ->
                currencyRateService.getCurrencyByRate(CURRENCY_RATE));
    }
}
