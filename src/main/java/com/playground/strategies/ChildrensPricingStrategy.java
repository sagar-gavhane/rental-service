package com.playground.strategies;

import java.math.BigDecimal;

public class ChildrensPricingStrategy implements PricingStrategy {
    private final double CHILDREN_MOVIE_PRICE = 1.5;

    @Override
    public double calculatePrice(int movieRentalDays) {
        if (movieRentalDays < 2) {
            return CHILDREN_MOVIE_PRICE;
        }

        BigDecimal price = BigDecimal.valueOf(((movieRentalDays - 3) * 1.5) + CHILDREN_MOVIE_PRICE)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

        return price.doubleValue();
    }
}
