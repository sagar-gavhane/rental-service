package com.playground.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieStoreTest {
    private final MovieStore movieStore = MovieStore.getInstance();

    @Test
    @DisplayName("should be able to return correct size of movie store")
    void shouldBeAbleToReturnCorrectSizeOfMovieStore() {
        assertEquals(4, movieStore.getMovies().size());
    }

    @Test
    @DisplayName("should be able to return correct movie by movie id")
    void shouldBeAbleToReturnCorrectMovieByMovieId() {
        assertEquals("You've Got Mail", movieStore.getMovieByMovieId("F001").get().title());
    }
}