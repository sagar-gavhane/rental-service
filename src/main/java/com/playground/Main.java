package com.playground;

import com.playground.entities.Customer;
import com.playground.entities.MovieRental;
import com.playground.services.RentalService;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 0 frequent points\n";

            List<MovieRental> movieRentals = Arrays.asList(new MovieRental("F001", 3), new MovieRental("F002", 1));

            Customer customer = new Customer("C. U. Stomer", movieRentals);

            String result = new RentalService().statement(customer);

            if (!result.equals(expected)) {
                throw new AssertionError("Expected: " + System.lineSeparator() + String.format(expected) + System.lineSeparator() + System.lineSeparator() + "Got: " + System.lineSeparator() + result);
            }

            System.out.println("Success  - Movie rented cost \n " + result);
        } catch (IllegalArgumentException | NullPointerException runtimeException) {
            System.out.println("Runtime Exception: " + runtimeException.getMessage());
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.getMessage());
        }
    }
}