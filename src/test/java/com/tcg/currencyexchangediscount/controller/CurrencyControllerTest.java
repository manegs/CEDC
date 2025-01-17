package com.tcg.currencyexchangediscount.controller;

import com.tcg.currencyexchangediscount.model.Bill;
import com.tcg.currencyexchangediscount.service.CurrencyService;
import com.tcg.currencyexchangediscount.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CurrencyControllerTest {
    @Mock
    private CurrencyService currencyService;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private CurrencyController currencyController;

    @Test
    void testCalculatePayableAmount() {
        MockitoAnnotations.openMocks(this);

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setOriginalCurrency("USD");
        bill.setTargetCurrency("EUR");

        when(discountService.calculateDiscount(bill)).thenReturn(180.0);
        when(currencyService.getExchangeRate("USD", "EUR")).thenReturn(0.85);

        double result = currencyController.calculatePayableAmount(bill);
        assertEquals(153.0, result);
    }
}