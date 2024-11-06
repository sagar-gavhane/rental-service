package com.playground.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieRentalTest {
    private MovieRental movieRental = null;

    @BeforeEach
    void setUp() {
        movieRental = new MovieRental("F001", 3);
    }

    @AfterEach
    void tearDown() {
        movieRental = null;
    }

    @Test
    @DisplayName("should throw an error if movie id is null")
    void shouldThrowAnErrorIfMovieIdIsNull() {
        assertThrows(NullPointerException.class, () -> new MovieRental(null, 1));
    }

    @Test
    @DisplayName("should throw an error if rental days is negative")
    void shouldThrowAnErrorIfMovieIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> new MovieRental("Some Movie Id", -1));
    }

    @Test
    @DisplayName("should be able to create new movie rental with id and rental days")
    void shouldBeAbleToCreateNewMovieRental() {
        assertNotNull(movieRental);
        assertEquals("F001", movieRental.movieId());
        assertEquals(3, movieRental.days());
    }
}