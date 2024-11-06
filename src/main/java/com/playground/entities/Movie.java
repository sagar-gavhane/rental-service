package com.playground.entities;

import com.playground.enums.MovieCode;

import java.util.Objects;

public record Movie(String title, MovieCode code) {
    public Movie {
        // validate the input
        Objects.requireNonNull(title, "Movie title cannot be null");
        Objects.requireNonNull(code, "Movie code cannot be null");
    }
}
