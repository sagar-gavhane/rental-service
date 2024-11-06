package com.playground.entities;

import java.util.List;
import java.util.Objects;

public record Customer(String name, List<MovieRental> movieRentals) {
    public Customer {
        // validate the input
        Objects.requireNonNull(name, "Customer name cannot be null");
        Objects.requireNonNull(movieRentals, "Customer must have at least one rental");

        movieRentals.forEach(movieRental -> {
            if (movieRental == null) {
                throw new IllegalArgumentException("Rental cannot be null");
            }

            if (movieRental.days() < 0) {
                throw new IllegalArgumentException("Rental days cannot be negative");
            }

            if (movieRental.movieId().isBlank()) {
                throw new IllegalArgumentException("Movie ID cannot be blank");
            }
        });
    }
}
