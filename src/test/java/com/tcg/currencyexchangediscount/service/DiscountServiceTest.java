package com.tcg.currencyexchangediscount.service;

import com.tcg.currencyexchangediscount.model.Bill;
import com.tcg.currencyexchangediscount.model.DiscountType;
import com.tcg.currencyexchangediscount.model.Item;
import com.tcg.currencyexchangediscount.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DiscountServiceTest {
    @Autowired
    private DiscountService discountService;

    @Test
    void testEmployeeDiscount() {
        User user = new User();
        user.setDiscountType(DiscountType.EMPLOYEE);
        user.setCustomerSince(LocalDate.now().minusYears(3));

        Item item1 = new Item();
        item1.setName("Laptop");
        item1.setCategory("Electronics");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setName("Phone");
        item2.setCategory("Electronics");
        item2.setPrice(100);

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setItems(Arrays.asList(item1, item2));
        bill.setUser(user);

        double discount = discountService.calculateDiscount(bill);
        assertEquals(130.0, discount);
    }

    @Test
    void testAffiliateDiscount() {
        User user = new User();
        user.setDiscountType(DiscountType.AFFILIATE);
        user.setCustomerSince(LocalDate.now().minusYears(1));

        Item item1 = new Item();
        item1.setName("Laptop");
        item1.setCategory("Electronics");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setName("Phone");
        item2.setCategory("Electronics");
        item2.setPrice(100);

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setItems(Arrays.asList(item1, item2));
        bill.setUser(user);

        double discount = discountService.calculateDiscount(bill);
        assertEquals(170.0, discount);
    }

    @Test
    void testLoyalCustomerDiscount() {
        User user = new User();
        user.setDiscountType(DiscountType.NONE);
        user.setCustomerSince(LocalDate.now().minusYears(3));

        Item item1 = new Item();
        item1.setName("Laptop");
        item1.setCategory("Electronics");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setName("Phone");
        item2.setCategory("Electronics");
        item2.setPrice(100);

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setItems(Arrays.asList(item1, item2));
        bill.setUser(user);

        double discount = discountService.calculateDiscount(bill);
        assertEquals(180.0, discount);
    }

    @Test
    void testNoPercentageDiscount() {
        User user = new User();
        user.setDiscountType(DiscountType.NONE);
        user.setCustomerSince(LocalDate.now().minusYears(1));

        Item item1 = new Item();
        item1.setName("Laptop");
        item1.setCategory("Electronics");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setName("Phone");
        item2.setCategory("Electronics");
        item2.setPrice(100);

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setItems(Arrays.asList(item1, item2));
        bill.setUser(user);

        double discount = discountService.calculateDiscount(bill);
        assertEquals(190.0, discount);
    }

    @Test
    void testGroceryItemsNoPercentageDiscount() {
        User user = new User();
        user.setDiscountType(DiscountType.EMPLOYEE);
        user.setCustomerSince(LocalDate.now().minusYears(3));

        Item item1 = new Item();
        item1.setName("Apple");
        item1.setCategory("Grocery");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setName("Banana");
        item2.setCategory("Grocery");
        item2.setPrice(100);

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setItems(Arrays.asList(item1, item2));
        bill.setUser(user);

        double discount = discountService.calculateDiscount(bill);
        assertEquals(190.0, discount);
    }

    @Test
    void testMixedItemsDiscount() {
        User user = new User();
        user.setDiscountType(DiscountType.AFFILIATE);
        user.setCustomerSince(LocalDate.now().minusYears(1));

        Item item1 = new Item();
        item1.setName("Apple");
        item1.setCategory("Grocery");
        item1.setPrice(100);

        Item item2 = new Item();
        item2.setName("Laptop");
        item2.setCategory("Electronics");
        item2.setPrice(100);

        Bill bill = new Bill();
        bill.setTotalAmount(200);
        bill.setItems(Arrays.asList(item1, item2));
        bill.setUser(user);

        double discount = discountService.calculateDiscount(bill);
        assertEquals(180.0, discount);
    }
}