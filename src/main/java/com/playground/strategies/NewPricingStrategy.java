package com.playground.strategies;

import java.math.BigDecimal;

public class NewPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(int movieRentalDays) {
        BigDecimal price = new BigDecimal(movieRentalDays * 3).setScale(2, BigDecimal.ROUND_HALF_UP);
        return price.doubleValue();
    }
}
