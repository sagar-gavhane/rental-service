package com.playground.entities;

import java.util.Objects;

public record MovieRental(String movieId, int days) {
    public MovieRental {
        // validate the input
        Objects.requireNonNull(movieId, "Movie ID cannot be null");

        if (days < 0) {
            throw new IllegalArgumentException("Rental days cannot be negative");
        }
    }
}
