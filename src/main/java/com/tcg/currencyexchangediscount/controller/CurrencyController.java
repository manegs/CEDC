package com.tcg.currencyexchangediscount.controller;

import com.tcg.currencyexchangediscount.model.Bill;
import com.tcg.currencyexchangediscount.service.CurrencyService;
import com.tcg.currencyexchangediscount.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final DiscountService discountService;

    @Autowired
    public CurrencyController(CurrencyService currencyService, DiscountService discountService) {
        this.currencyService = currencyService;
        this.discountService = discountService;
    }

    @PostMapping("/calculate")
    public double calculatePayableAmount(@RequestBody Bill bill) {
        double discountedAmount = discountService.calculateDiscount(bill);
        double exchangeRate = currencyService.getExchangeRate(bill.getOriginalCurrency(), bill.getTargetCurrency());
        return discountedAmount * exchangeRate;
    }
}