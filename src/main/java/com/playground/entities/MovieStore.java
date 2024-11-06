package com.playground.entities;

import com.playground.enums.MovieCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Singleton class representing a movie store.
public class MovieStore {
    private static MovieStore instance = null;
    private final Map<String, Movie> movies = new HashMap<>();

    private MovieStore() {
        initialiseMovies();
    }

    // create a single instance of the movie store
    public static synchronized MovieStore getInstance() {
        if (instance == null) {
            instance = new MovieStore();
        }

        return instance;
    }

    // initialise the movie store
    private void initialiseMovies() {
        movies.put("F001", new Movie("You've Got Mail", MovieCode.REGULAR));
        movies.put("F002", new Movie("Matrix", MovieCode.REGULAR));
        movies.put("F003", new Movie("Cars", MovieCode.CHILDRENS));
        movies.put("F004", new Movie("Fast & Furious X", MovieCode.NEW));
    }

    // get a movie by its ID
    public Optional<Movie> getMovieByMovieId(String movieId) {
        return Optional.ofNullable(movies.get(movieId));
    }

    public Map<String, Movie> getMovies() {
        return Map.copyOf(movies);
    }
}
