package com.playground.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customer customer = null;

    @BeforeEach
    void setup() {
        String name = "C. U. Stomer";
        List<MovieRental> movieRental = List.of(new MovieRental("F001", 3));
        customer = new Customer(name, movieRental);
    }

    @AfterEach
    void tearDown() {
        customer = null;
    }

    @Test
    @DisplayName("should be able to create customer with name")
    void shouldBeAbleToCreateCustomerWithName() {
        assertNotNull(customer.name());
        assertEquals("C. U. Stomer", customer.name());
    }

    @Test
    @DisplayName("should be able to create customer with movie rentals")
    void shouldBeAbleToCreateCustomerWithMovieRentals() {
        assertNotNull(customer.movieRentals());
        assertEquals(customer.movieRentals(), List.of(new MovieRental("F001", 3)));
    }

    @Test
    @DisplayName("should throw an error if customer name is null")
    void shouldThrowAnErrorIfCustomerNameIsNull() {
        assertThrows(NullPointerException.class, () -> new Customer(null, List.of()));
    }

    @Test
    @DisplayName("should throw an error if movie rental is null")
    void shouldThrowAnErrorIfMovieRentalIsNull() {
        assertThrows(NullPointerException.class, () -> new Customer("C. U. Stomer", null));
    }

    @Test
    @DisplayName("should throw an error if movie rental days is negative")
    void shouldThrowAnErrorIfMovieRentalDaysIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("C. U. Stomer", List.of(new MovieRental("F001", -1))));
    }

    @Test
    @DisplayName("should throw an error if movie rental id is blank")
    void shouldThrowAnErrorIfMovieRentalIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("C. U. Stomer", List.of(new MovieRental("", 1))));
    }
}