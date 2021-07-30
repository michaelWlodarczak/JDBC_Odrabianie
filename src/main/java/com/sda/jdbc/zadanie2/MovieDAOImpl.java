package com.sda.jdbc.zadanie2;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieDAOImpl implements MovieDAO {

    public final Connection connection;

    public MovieDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void createTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE MOVIES (id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "title VARCHAR(255), genre VARCHAR(255), yearOfRelease INTEGER)";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DataBaseActionException("Problem with query execution", e);
        }
    }

    @Override
    public void deleteTable() {
        try (Statement statement = connection.createStatement()) {
            String sql = "DROP TABLE IF EXISTS MOVIES";
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DataBaseActionException("Problem with table deletion", e);
        }
    }

    @Override
    public void createMovie(Movie movie) {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("INSERT INTO MOVIES (title, genre, yearOfRelease) values (?,?,?)")) {
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getGenre());
            preparedStatement.setInt(3, movie.getYearOfRelease());
            int result = preparedStatement.executeUpdate();
            System.out.println(result + " row inserted.");
            System.out.println(movie.getTitle());
        } catch (SQLException e) {
            throw new DataBaseActionException("Problem with inserting movie", e);
        }
    }

    @Override
    public void deleteMovie(int id) {
        try (PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM MOVIES WHERE id = ?")) {
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            System.out.println(result + " row deleted.");
            //TODO ?? dobranie sie do tego w sout
        } catch (SQLException e) {
            throw new DataBaseActionException("Problem with movie deletion", e);
        }
    }

    @Override
    public void updateMoviesTitle(int id, String newTitle) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE MOVIES SET title = ? WHERE id = ?")) {
            statement.setString(1, newTitle);
            statement.setInt(2, id);
            int result = statement.executeUpdate();
            System.out.println(result + " row updated");
            //System.out.println(new Movie().getTitle());
        } catch (SQLException e) {
            throw new DataBaseActionException("Problem with movie update", e);
        }
    }

    @Override
    public Optional<Movie> findMovieById(int id) {
        String sql = "SELECT * FROM MOVIES WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            boolean isMovieFound = statement.execute();
            if (isMovieFound){
                ResultSet foundMovie = statement.getResultSet();
                if (foundMovie.next()){
                    //Movie
                    String title = foundMovie.getString(2);
                    String genre = foundMovie.getString(3);
                    Integer yearOfRelease = foundMovie.getInt(4);
                    return Optional.of(new Movie(id,title,genre,yearOfRelease));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataBaseActionException("Cannot find movie with this ID", e);
        }
    }

    @Override
    public List<Movie> findAll() {
        return null;
    }
}
