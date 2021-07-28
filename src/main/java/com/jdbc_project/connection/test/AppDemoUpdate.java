package com.jdbc_project.connection.test;

import com.jdbc_project.connection.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AppDemoUpdate {
    private static final String SQL_UPDATE = "UPDATE Products SET Price = Price - 5000";
    private static final String SQL_UPDATE2 = "UPDATE Products SET Price = Price - 5000 where Id = 2";



    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()){
            System.out.println("Connection successful");
            Statement statement = connection.createStatement();
            int n = statement.executeUpdate(SQL_UPDATE);
            System.out.println("Database has been updated " + n + " objects");



        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println("Connection unsuccessful");
            throwables.printStackTrace();
        }

    }
}
