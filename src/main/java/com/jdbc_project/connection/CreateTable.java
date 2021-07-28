package com.jdbc_project.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    private static final String SQL_CREATE_TABLE = "CREATE TABLE persons (Id INT PRIMARY KEY AUTO_INCREMENT, FirstName VARCHAR(20), LastName VARCHAR(20), Age INT)";

    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()){
            System.out.println("Connection successful");
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL_CREATE_TABLE);
            System.out.println("Database has been created!");


        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Connection unsuccessful");
            throwables.printStackTrace();
        }
    }
}
