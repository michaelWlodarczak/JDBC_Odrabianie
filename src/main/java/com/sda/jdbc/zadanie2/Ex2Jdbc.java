package com.sda.jdbc.zadanie2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Ex2Jdbc {

    //MySQL
    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "Hania127";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_USER, MYSQL_PASSWORD)) {
            MovieDAO movieDAOImpl = new MovieDAOImpl(connection); //polimorfizm
            movieDAOImpl.deleteTable();
            movieDAOImpl.createTable();

            Movie godfather = new Movie("The God Father", "Drama", 1974);
            Movie godfather2 = new Movie("The God Father II", "Drama", 1980);
            Movie godfather3 = new Movie("The God Father III", "Drama", 1990);

            movieDAOImpl.createMovie(godfather);
            movieDAOImpl.createMovie(godfather2);
            movieDAOImpl.createMovie(godfather3);

            movieDAOImpl.deleteMovie(3);

            movieDAOImpl.updateMoviesTitle(2, "The Godfather 2");

//            Optional<Movie> godfather1 = movieDAOImpl.findMovieById(15);
//            if (godfather1.isPresent()) {
//                System.out.println(godfather1.get());
//            } else {
//                System.out.println("Movie not found!");
//            }
            System.out.println(movieDAOImpl.findMovieById(15));
            //System.out.println(movieDAOImpl.findMovieById(16).get()); NoSuchElementException
            System.out.println(movieDAOImpl.findMovieById(2));

            movieDAOImpl.findMovieById(2).ifPresent(System.out::println);
            movieDAOImpl.findMovieById(12).ifPresent(System.out::println);



        } catch (SQLException e) {
            throw new DataBaseActionException("Problem with connection", e);
        }

    }
}
