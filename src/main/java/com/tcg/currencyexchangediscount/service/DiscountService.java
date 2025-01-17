package com.tcg.currencyexchangediscount.service;

import com.tcg.currencyexchangediscount.model.Bill;
import com.tcg.currencyexchangediscount.model.DiscountType;
import com.tcg.currencyexchangediscount.model.Item;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DiscountService {
    public double calculateDiscount(Bill bill) {
        double discount = 0.0;
        double totalAmount = bill.getTotalAmount();
        List<Item> items = bill.getItems();

        // Calculate percentage-based discount
        double percentageDiscount = 0.0;
        if (bill.getUser().getDiscountType() == DiscountType.EMPLOYEE) {
            percentageDiscount = 0.30;
        } else if (bill.getUser().getDiscountType() == DiscountType.AFFILIATE) {
            percentageDiscount = 0.10;
        } else if (ChronoUnit.YEARS.between(bill.getUser().getCustomerSince(), LocalDate.now()) > 2) {
            percentageDiscount = 0.05;
        }

        // Apply percentage-based discount only to non-grocery items
        double nonGroceryTotal = items.stream()
                .filter(item -> !item.getCategory().equalsIgnoreCase("grocery"))
                .mapToDouble(Item::getPrice)
                .sum();
        discount += nonGroceryTotal * percentageDiscount;

        // Apply $5 discount for every $100 on the bill
        discount += (int) (totalAmount / 100) * 5;

        return totalAmount - discount;
    }
}