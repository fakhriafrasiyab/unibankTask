package com.example.unitech.controller;

import com.example.unitech.entity.Currency;
import com.example.unitech.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyRateController {
    private final CurrencyRateService currencyRateService;

    @GetMapping
    public Currency getCurrencyByRate(@RequestParam String currencyRate) {
        return currencyRateService.getCurrencyByRate(currencyRate);
    }
}
