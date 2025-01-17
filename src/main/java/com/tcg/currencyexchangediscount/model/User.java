package com.tcg.currencyexchangediscount.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private String name;
    private DiscountType discountType;
    private LocalDate customerSince;
}