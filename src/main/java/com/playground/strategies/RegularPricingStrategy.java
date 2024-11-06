package com.playground.strategies;

import java.math.BigDecimal;

public class RegularPricingStrategy implements PricingStrategy {
    private final double NORMAL_MOVIE_PRICE = 2;

    @Override
    public double calculatePrice(int movieRentalDays) {
        if (movieRentalDays < 2) {
            return NORMAL_MOVIE_PRICE;
        }

        BigDecimal price = new BigDecimal(((movieRentalDays - 2) * 1.5) + NORMAL_MOVIE_PRICE)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        return price.doubleValue();
    }
}
