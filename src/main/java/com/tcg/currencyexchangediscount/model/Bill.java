package com.tcg.currencyexchangediscount.model;

import lombok.Data;

import java.util.List;

@Data
public class Bill {
    private List<Item> items;
    private double totalAmount;
    private String originalCurrency;
    private String targetCurrency;
    private User user;
}