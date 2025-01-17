package com.tcg.currencyexchangediscount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyService {

    private String apiUrl;
    private String apiKey;
    private RestTemplate restTemplate;

    @Autowired
    public CurrencyService(@Value("${exchange.api.url}") String apiUrl, @Value("${exchange.api.key}") String apiKey, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }

    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        String url = apiUrl + baseCurrency + "?apikey=" + apiKey;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        Double rate = rates.get(targetCurrency);
        return rate != null ? rate : 0.0;
    }
}