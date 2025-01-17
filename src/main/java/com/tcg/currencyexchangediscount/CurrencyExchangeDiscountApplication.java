package com.tcg.currencyexchangediscount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CurrencyExchangeDiscountApplication {
    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeDiscountApplication.class, args);
    }
}