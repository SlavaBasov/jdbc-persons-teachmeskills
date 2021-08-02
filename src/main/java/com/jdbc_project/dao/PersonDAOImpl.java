package com.jdbc_project.dao;

import com.jdbc_project.connection.DBConnection;
import com.jdbc_project.model.Person;
import com.jdbc_project.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl implements BaseDAO<Person>{
    final static String INSERT_PERSON = "INSERT Persons(FirstName, LastName, Age, role_id) VALUES (?, ?, ?, ?)";
    final static String UPDATE_PERSON = "UPDATE persons SET firstName = ?, lastName = ?, age = ?, role_id = ? WHERE Id = ?";
    final static String DELETE_PERSON = "DELETE FROM persons WHERE Id = ? AND FirstName = ? AND LastName = ? AND Age = ? AND role_id = ?";
    final static String FIND_PERSON = "SELECT * FROM persons WHERE ID = ?";
    final static String FIND_ALL_PERSONS = "SELECT * FROM persons";
    RoleDAOImpl roleDAO = new RoleDAOImpl();

    @Override
    public boolean add(Person person) {
        int rows = 0;
        int id = 0;
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setInt(4, person.getRole().getId());

            rows = preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            System.out.printf("Person %s %s is successfully added! Id = %d\n", person.getFirstName(), person.getLastName(), id);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rows!=0;
    }

    @Override
    public boolean update(Person person){
        int id = person.getId();
        Person oldPerson = findById(id);
        if (id != 0) {
            if (person.equals(oldPerson))
                System.out.printf("Person '%s %s' (id = %d) does not require updating\n", person.getFirstName(),
                        person.getLastName(), person.getId());
            else {
                try (Connection connection = DBConnection.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PERSON);
                    preparedStatement.setString(1, person.getFirstName());
                    preparedStatement.setString(2, person.getLastName());
                    preparedStatement.setInt(3, person.getAge());
                    preparedStatement.setInt(4, person.getRole().getId());
                    preparedStatement.setInt(5, person.getId());
                    preparedStatement.executeUpdate();

                    System.out.printf("Person %d %s %s is successfully updated. New person is: \n", oldPerson.getId(), oldPerson.getFirstName(),
                            oldPerson.getLastName());
                    System.out.println(person);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return id != 0;
    }

    @Override
    public boolean delete(Person person) {
        int rows = 0;

        if (person.getId() == 0) System.out.println("Person is not found");
        else {
            Person personTest = person;
            try (Connection connection = DBConnection.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PERSON);
                preparedStatement.setInt(1, person.getId());
                preparedStatement.setString(2, person.getFirstName());
                preparedStatement.setString(3, person.getLastName());
                preparedStatement.setInt(4, person.getAge());
                preparedStatement.setInt(5, person.getRole().getId());

                rows = preparedStatement.executeUpdate();
                System.out.printf("Person %s %s (id = %d) is successfully deleted!\n", person.getFirstName(), person.getLastName(), person.getId());

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return rows!=0;
    }

    @Override
    public boolean deleteById(int id) {
        int rows = 0;
        Person person = findById(id);
        delete(person);
        return rows!=0;
    }

    @Override
    public void deleteAll(){
        List<Person> personList = findAll();
        for(Person p: personList){
            delete(p);
        }
    }

    @Override
    public Person findById(int id) {
        Person person = new Person();
        try (Connection connection = DBConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_PERSON);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                person = new Person(id, resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), roleDAO.findById(resultSet.getInt(5)));
            } else {
                System.out.println("Person is not found.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_PERSONS);
            while (resultSet.next()){
                personList.add(new Person(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), roleDAO.findById(resultSet.getInt(5))));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return personList;
    }

}
