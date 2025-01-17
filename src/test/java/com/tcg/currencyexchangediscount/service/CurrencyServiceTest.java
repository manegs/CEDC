package com.tcg.currencyexchangediscount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private static final String EXCHANGE_API_URL = "https://api.exchangeratesapi.io/latest?base=";
    private static final String EXCHANGE_API_KEY = "YOUR_API_KEY";

    private CurrencyService currencyService;

    @BeforeEach
    public void setUp() {
        currencyService = new CurrencyService(EXCHANGE_API_URL, EXCHANGE_API_KEY, restTemplate);
    }


    @Test
    void testGetExchangeRateSuccess() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR", 0.85);
        response.put("rates", rates);

        when(restTemplate.getForObject(anyString(), any())).thenReturn(response);

        double rate = currencyService.getExchangeRate("USD", "EUR");
        assertEquals(0.85, rate);
    }

    @Test
    void testGetExchangeRateInvalidCurrency() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Double> rates = new HashMap<>();
        response.put("rates", rates);

        when(restTemplate.getForObject(anyString(), any())).thenReturn(response);

        double rate = currencyService.getExchangeRate("USD", "INVALID");
        assertEquals(0.0, rate);
    }

    @Test
    void testGetExchangeRateApiFailure() {
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException("API failure"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyService.getExchangeRate("USD", "EUR");
        });

        assertEquals("API failure", exception.getMessage());
    }
}