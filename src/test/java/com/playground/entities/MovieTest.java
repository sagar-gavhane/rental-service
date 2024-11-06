package com.playground.entities;

import com.playground.enums.MovieCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    Movie regularMovie = null;
    Movie newMovie = null;
    Movie childrenMovie = null;

    @BeforeEach
    void setup() {
        regularMovie = new Movie("Test Regular Movie", MovieCode.REGULAR);
        newMovie = new Movie("Test New Movie", MovieCode.NEW);
        childrenMovie = new Movie("Test Children Movie", MovieCode.CHILDRENS);
    }

    @AfterEach
    void tearDown() {
        regularMovie = null;
        newMovie = null;
        childrenMovie = null;
    }

    @Test
    @DisplayName("should be able to create movie with title")
    void shouldBeAbleToCreateMovieWithName() {
        assertNotNull(newMovie);
        assertEquals("Test New Movie", newMovie.title());
    }

    @Test
    @DisplayName("should be able to create regular movie")
    void shouldBeAbleToCreateRegularMovie() {
        assertNotNull(regularMovie);
        assertEquals(MovieCode.REGULAR, regularMovie.code());
    }

    @Test
    @DisplayName("should be able to create new movie")
    void shouldBeAbleToCreateNewMovie() {
        assertNotNull(newMovie);
        assertEquals(MovieCode.NEW, newMovie.code());
    }

    @Test
    @DisplayName("should be able to create children movie")
    void shouldBeAbleToCreateChildrenMovie() {
        assertNotNull(childrenMovie);
        assertEquals(MovieCode.CHILDRENS, childrenMovie.code());
    }

    @Test
    @DisplayName("should throw an error if movie name is null")
    void shouldThrowAnErrorIfMovieNameIsNull() {
        assertThrows(NullPointerException.class, () -> new Movie(null, MovieCode.REGULAR));
    }

    @Test
    @DisplayName("should throw an error if movie code is null")
    void shouldThrowAnErrorIfMovieCodeIsNull() {
        assertThrows(NullPointerException.class, () -> new Movie("Test Movie", null));
    }
}