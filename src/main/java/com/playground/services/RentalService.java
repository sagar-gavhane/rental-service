package com.playground.services;

import com.playground.entities.Customer;
import com.playground.entities.Movie;
import com.playground.entities.MovieRental;
import com.playground.entities.MovieStore;
import com.playground.enums.MovieCode;
import com.playground.strategies.ChildrensPricingStrategy;
import com.playground.strategies.NewPricingStrategy;
import com.playground.strategies.PricingStrategy;
import com.playground.strategies.RegularPricingStrategy;

import java.math.BigDecimal;
import java.util.*;


// Service class responsible for handling rental operations.
public class RentalService {
    // Instance of the MovieStore class, which is a singleton.
    private final MovieStore movies = MovieStore.getInstance();

    // Map to store pricing strategies based on movie code.
    private final EnumMap<MovieCode, PricingStrategy> pricingStrategyMap = new EnumMap<>(MovieCode.class);

    public RentalService() {
        pricingStrategyMap.put(MovieCode.REGULAR, new RegularPricingStrategy());
        pricingStrategyMap.put(MovieCode.NEW, new NewPricingStrategy());
        pricingStrategyMap.put(MovieCode.CHILDRENS, new ChildrensPricingStrategy());
    }

    /**
     * Returns a map of movies and their corresponding prices.
     * <p>
     * This method takes a list of movie rentals and retrieves the corresponding movies from
     * the movie store. It then calculates the price of each movie rental using the pricing
     * strategy based on the movie code.
     *
     * @param movieRentals the list of movie rentals
     * @return a map of movies and their corresponding prices
     */
    public Map<Movie, Double> getMoviePriceMap(List<MovieRental> movieRentals) {
        Map<Movie, Double> moviePriceMap = new LinkedHashMap<>();

        for (MovieRental movieRental : movieRentals) {
            String movieId = movieRental.movieId();
            Optional<Movie> optionalMovie = movies.getMovieByMovieId(movieId);

            if (optionalMovie.isEmpty()) {
                throw new IllegalArgumentException("Movie not found");
            }

            Movie movie = optionalMovie.get();
            MovieCode movieCode = movie.code();

            double rawPrice = pricingStrategyMap.get(movieCode).calculatePrice(movieRental.days());

            double precisionPrice = BigDecimal.valueOf(rawPrice)
                    .setScale(2, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();

            moviePriceMap.put(movie, precisionPrice);
        }

        return moviePriceMap;
    }

    /**
     * Returns the total amount owed for a list of movie rentals.
     * <p>
     * This method calculates the total amount owed by summing up the prices of all the
     * movie rentals. It uses the Strategy design pattern to delegate the pricing calculation
     * to the corresponding pricing strategy based on the movie code.
     *
     * @param movieRentals the list of movie rentals
     * @return the total amount owed
     */
    public double getTotalAmount(List<MovieRental> movieRentals) {
        Map<Movie, Double> moviePriceMap = getMoviePriceMap(movieRentals);
        double rawTotalAmount = moviePriceMap.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return BigDecimal.valueOf(rawTotalAmount)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }

    /**
     * Returns the total frequent enter points earned from a list of movie rentals.
     * <p>
     * This method iterates through each movie rental and checks if the movie is a new release
     * and if the rental period exceeds 2 days. If both conditions are met, it increments the
     * frequent enter points counter.
     *
     * @param movieRentals the list of movie rentals
     * @return the total frequent enter points earned
     */
    public int getFrequentEnterPoints(List<MovieRental> movieRentals) {
        int frequentEnterPoints = 0;

        for (MovieRental movieRental : movieRentals) {
            String movieId = movieRental.movieId();
            Optional<Movie> optionalMovie = movies.getMovieByMovieId(movieId);

            if (optionalMovie.isEmpty()) {
                throw new IllegalArgumentException("Movie not found");
            }

            Movie movie = optionalMovie.get();
            MovieCode movieCode = movie.code();
            int movieRentalDays = movieRental.days();

            if (movieCode == MovieCode.NEW && movieRentalDays > 2) {
                frequentEnterPoints++;
            }
        }

        return frequentEnterPoints;
    }

    /**
     * Generates a statement for a customer's movie rentals.
     * <p>
     * This method takes a customer object as input and returns a string statement that includes
     * the customer's name, a list of rented movies with their corresponding prices, the total
     * amount owed, and the number of frequent enter points earned.
     *
     * @param customer the customer object
     * @return the statement as a string
     */
    public String statement(Customer customer) {
        String customerName = customer.name();
        List<MovieRental> movieRentals = customer.movieRentals();

        Map<Movie, Double> moviePriceMap = getMoviePriceMap(movieRentals);
        double totalAmount = getTotalAmount(movieRentals);
        int frequentEnterPoints = getFrequentEnterPoints(movieRentals);

        StringBuilder result = new StringBuilder("Rental Record for %s%n".formatted(customerName));

        for (Map.Entry<Movie, Double> entry : moviePriceMap.entrySet()) {
            result.append("\t %s %.1f%n".formatted(entry.getKey().title(), entry.getValue()));
        }

        result.append("Amount owed is %.1f%n".formatted(totalAmount));
        result.append("You earned %d frequent points%n".formatted(frequentEnterPoints));

        return result.toString();
    }
}
