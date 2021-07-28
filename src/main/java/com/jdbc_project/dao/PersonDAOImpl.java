package com.jdbc_project.dao;

import com.jdbc_project.connection.DBConnection;
import com.jdbc_project.model.Person;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class PersonDAOImpl implements BaseDAO<Person>{
    final static String INSERT_PEOPLE = "INSERT Persons(FirstName, LastName, Age) VALUES (?, ?, ?)";
    final static String DELETE_PEOPLE = "DELETE FROM persons WHERE ID = ?";
    final static String FIND_PEOPLE = "SELECT * FROM persons WHERE ID = ?";


    @Override
    public boolean add(Person person) {
        int rows = 0;
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PEOPLE);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getAge());

            rows = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rows!=0;
    }

    @Override
    public boolean delete(Person person) {
        int rows = 0;
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PEOPLE);
            preparedStatement.setInt(1, person.getId());
            rows = preparedStatement.executeUpdate();
            System.out.printf("Person with id = %d is successfully deleted!\n", person.getId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return rows!=0;
    }

    public boolean delete(int id) {
        int rows = 0;
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PEOPLE);
            preparedStatement.setInt(1, id);
            rows = preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        return rows!=0;
    }

    @Override
    public Person findById(int id) {
        Person person = null;
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PEOPLE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            person = new Person(id, resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        return null;
    }
}
