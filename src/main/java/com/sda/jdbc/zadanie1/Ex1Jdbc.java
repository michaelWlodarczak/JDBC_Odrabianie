package com.sda.jdbc.zadanie1;

import java.sql.*;

public class Ex1Jdbc {

    //H2
    // private static final String H2_DRIVER = "org.h2.Driver"; // - bymc moze nie potrzebne
    private static final String DB_URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    //MySQL
    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "Hania127";

//    Server: sql11.freemysqlhosting.net
//    Name: sql11428271
//    Username: sql11428271
//    Password: cgSQvER6Lk
//    Port number: 3306
    // --- polaczenie z baza internetowa

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_USER, MYSQL_PASSWORD)) {
            System.out.println(connection.getCatalog());

            try (Statement statement = connection.createStatement()) {
                statement.execute("DROP TABLE IF EXISTS MOVIES");

                statement.execute("CREATE TABLE MOVIES (id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                        "title VARCHAR(255), genre VARCHAR(255), yearOfRelease INTEGER)");

                statement.execute("INSERT INTO MOVIES (title, genre, yearOfRelease) " +
                        "VALUES ('Matrix', 'Action', 1999)");
                statement.execute("INSERT INTO MOVIES (title, genre, yearOfRelease) " +
                        "VALUES ('Matrix2', 'Action', 2002)");
                statement.execute("INSERT INTO MOVIES (title, genre, yearOfRelease) " +
                        "VALUES ('Matrix3', 'Action', 2003)");
                statement.execute("INSERT INTO MOVIES (title, genre, yearOfRelease) " +
                        "VALUES ('Matrix4', 'Action', 2022)");

                statement.execute("DELETE FROM MOVIES WHERE id=4");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String updateSql = "UPDATE MOVIES SET yearOfRelease = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
                preparedStatement.setInt(1, 2003); // parametr pierwszy, wartość: 2003
                preparedStatement.setInt(2, 2); // parametr drugi, wartość: 2
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM MOVIES");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    int yearOfRelease = rs.getInt("yearOfRelease");

                    System.out.println("###");
                    System.out.println("ID:" + id);
                    System.out.println("Title:" + title);
                    System.out.println("Genre:" + genre);
                    System.out.println("Year of release:" + yearOfRelease);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
