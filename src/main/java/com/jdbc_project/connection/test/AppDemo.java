package com.jdbc_project.connection.test;

import com.jdbc_project.connection.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDemo {
    private static final String SQL_CREATE_TABLE = "CREATE TABLE products (Id INT PRIMARY KEY AUTO_INCREMENT, ProductName VARCHAR(20), Price INT)";
    private static final String SQL_ADD_ELEMENT = "INSERT Products(ProductName, Price) VALUES ('IPhone X', 76000), ('galaxy s9', 56600)";


    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()){
            System.out.println("Connection successful");
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQL_CREATE_TABLE);
            System.out.println("Database has been created!");

            int rows = statement.executeUpdate(SQL_ADD_ELEMENT);
            System.out.printf("Added %d rows", rows);

        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Connection unsuccessful");
            throwables.printStackTrace();
        }

    }
}
