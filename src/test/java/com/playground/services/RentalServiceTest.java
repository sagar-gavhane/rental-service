package com.playground.services;

import com.playground.entities.Customer;
import com.playground.entities.Movie;
import com.playground.entities.MovieRental;
import com.playground.entities.MovieStore;
import com.playground.enums.MovieCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RentalServiceTest {
    private RentalService rentalService;
    private MovieStore movieStore;

    @BeforeEach
    void setUp() {
        movieStore = mock(MovieStore.class);
        rentalService = new RentalService();
    }

    @Test
    @DisplayName("should return regular movie with price")
    void shouldReturnMoviePriceMap() {
        MovieStore movieStore = MovieStore.getInstance();
        MovieRental movieRental = new MovieRental("F001", 3);

        RentalService rentalService = new RentalService();
        Map<Movie, Double> moviePriceMap = rentalService.getMoviePriceMap(List.of(movieRental));

        assertEquals(1, moviePriceMap.size());
        assertEquals(3.5, moviePriceMap.get(movieStore.getMovieByMovieId("F001").get()));
    }

    @Test
    @DisplayName("should calculate prices correctly")
    void shouldCalculatePricesCorrectly() {
        Movie regularMovie = new Movie("You've Got Mail", MovieCode.REGULAR);
        Movie newMovie = new Movie("Fast & Furious X", MovieCode.NEW);
        Movie childrenMovie = new Movie("Cars", MovieCode.CHILDRENS);

        when(movieStore.getMovieByMovieId("F001")).thenReturn(java.util.Optional.of(regularMovie));
        when(movieStore.getMovieByMovieId("F004")).thenReturn(java.util.Optional.of(newMovie));
        when(movieStore.getMovieByMovieId("F003")).thenReturn(java.util.Optional.of(childrenMovie));

        List<MovieRental> movieRentals = Arrays.asList(
                new MovieRental("F001", 3),
                new MovieRental("F004", 5),
                new MovieRental("F003", 4)
        );

        var moviePriceMap = rentalService.getMoviePriceMap(movieRentals);

        assertEquals(3.5, moviePriceMap.get(regularMovie));
        assertEquals(15.0, moviePriceMap.get(newMovie));
        assertEquals(3.0, moviePriceMap.get(childrenMovie));
    }

    @Test
    @DisplayName("should calculate total amount correctly")
    void shouldCalculateTotalAmount() {
        Movie regularMovie = new Movie("You've Got Mail", MovieCode.REGULAR);
        Movie newMovie = new Movie("Fast & Furious X", MovieCode.NEW);
        Movie childrenMovie = new Movie("Cars", MovieCode.CHILDRENS);

        when(movieStore.getMovieByMovieId("F001")).thenReturn(java.util.Optional.of(regularMovie));
        when(movieStore.getMovieByMovieId("F004")).thenReturn(java.util.Optional.of(newMovie));
        when(movieStore.getMovieByMovieId("F003")).thenReturn(java.util.Optional.of(childrenMovie));

        List<MovieRental> movieRentals = Arrays.asList(
                new MovieRental("F001", 3),
                new MovieRental("F004", 5),
                new MovieRental("F003", 4)
        );

        double totalAmount = rentalService.getTotalAmount(movieRentals);

        assertEquals(21.5, totalAmount);
    }

    @Test
    @DisplayName("should calculate frequent enter points correctly")
    void shouldCalculateFrequentEnterPoints() {
        Movie regularMovie = new Movie("You've Got Mail", MovieCode.REGULAR);
        Movie newMovie = new Movie("Fast & Furious X", MovieCode.NEW);
        Movie childrenMovie = new Movie("Cars", MovieCode.CHILDRENS);

        when(movieStore.getMovieByMovieId("F001")).thenReturn(java.util.Optional.of(regularMovie));
        when(movieStore.getMovieByMovieId("F004")).thenReturn(java.util.Optional.of(newMovie));
        when(movieStore.getMovieByMovieId("F003")).thenReturn(java.util.Optional.of(childrenMovie));

        List<MovieRental> movieRentals = Arrays.asList(
                new MovieRental("F001", 3),
                new MovieRental("F004", 5),
                new MovieRental("F003", 4)
        );

        int frequentEnterPoints = rentalService.getFrequentEnterPoints(movieRentals);

        assertEquals(1, frequentEnterPoints);
    }

    @Test
    @DisplayName("should generate statement correctly")
    void shouldGenerateStatement() {
        Movie regularMovie = new Movie("You've Got Mail", MovieCode.REGULAR);
        Movie newMovie = new Movie("Fast & Furious X", MovieCode.NEW);
        Movie childrenMovie = new Movie("Cars", MovieCode.CHILDRENS);

        when(movieStore.getMovieByMovieId("F001")).thenReturn(java.util.Optional.of(regularMovie));
        when(movieStore.getMovieByMovieId("F004")).thenReturn(java.util.Optional.of(newMovie));
        when(movieStore.getMovieByMovieId("F003")).thenReturn(java.util.Optional.of(childrenMovie));

        List<MovieRental> movieRentals = Arrays.asList(
                new MovieRental("F001", 3),
                new MovieRental("F004", 5),
                new MovieRental("F003", 4)
        );

        Customer customer = new Customer("John Doe", movieRentals);

        String statement = rentalService.statement(customer);

        String expectedStatement = """
                Rental Record for John Doe
                \t You've Got Mail 3.5
                \t Fast & Furious X 15.0
                \t Cars 3.0
                Amount owed is 21.5
                You earned 1 frequent points
                """;

        assertEquals(expectedStatement, statement);
    }
}