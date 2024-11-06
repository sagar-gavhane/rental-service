package com.playground.strategies;

import com.playground.enums.MovieCode;

public class PricingStrategyManager {
    private PricingStrategy pricingStrategy;

    public PricingStrategy setPricingStrategy(MovieCode movieCode) {
        this.pricingStrategy = switch (movieCode) {
            case NEW -> new NewPricingStrategy();
            case REGULAR -> new RegularPricingStrategy();
            case CHILDRENS -> new ChildrensPricingStrategy();
            default -> throw new IllegalArgumentException("No pricing strategy selected");
        };

        return this.pricingStrategy;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }
}
