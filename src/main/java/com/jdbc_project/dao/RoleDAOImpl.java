package com.jdbc_project.dao;

import com.jdbc_project.connection.DBConnection;
import com.jdbc_project.model.Person;
import com.jdbc_project.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAOImpl implements BaseDAO<Role> {
    final static String INSERT_ROLE = "INSERT role(name) VALUES (?)";
    final static String DELETE_ROLE = "DELETE FROM role WHERE id = ? AND name = ?";
    final static String FIND_ROLE = "SELECT * FROM role WHERE ID = ?";
    final static String FIND_ALL_ROLES = "SELECT * FROM role";
    final static String UPDATE_ROLE = "UPDATE role SET name = ? WHERE Id = ?";


    @Override
    public boolean add(Role role) {
        int rows = 0;
        Role roleFound = new Role();
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, role.getName());

            rows = preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                roleFound = findById(generatedKeys.getInt(1));
                System.out.printf("Role %s is successfully created with id = %d\n", roleFound.getName(), roleFound.getId());

            } else throw new SQLException("Creating user failed, no ID obtained.");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rows != 0;
    }

    @Override
    public boolean delete(Role role) {
        int rows = 0;

        if (role.getId() == 0) System.out.println("Role is not found");
        else {

            try (Connection connection = DBConnection.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE);
                preparedStatement.setInt(1, role.getId());
                preparedStatement.setString(2, role.getName());
                rows = preparedStatement.executeUpdate();

                System.out.printf("Role %s with id = %d is successfully deleted\n", role.getName(), role.getId());


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return rows != 0;
    }

    @Override
    public boolean deleteById(int id) {
        int rows = 0;
        Role role = findById(id);
        delete(role);
        return rows!=0;
    }

    @Override
    public void deleteAll() {
        List<Role> roleList = findAll();
        for (Role role : roleList) {
            delete(role);
        }
    }

    @Override
    public boolean update(Role role) {
        int id = role.getId();
        Role oldRole = findById(id);
        if (id != 0) {
            if(role.equals(oldRole)) System.out.printf("Role '%s' (id = %d) does not require updating\n", role.getName(), role.getId());
            else {
                try (Connection connection = DBConnection.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE);
                    preparedStatement.setString(1, role.getName());
                    preparedStatement.setInt(2, role.getId());
                    preparedStatement.executeUpdate();


                    System.out.printf("Role '%s' (id = %d) is successfully updated. New role is: \n", role.getName(), role.getId());
                    System.out.println(role);

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
    public Role findById(int id) {
        Role role = new Role();
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROLE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                role = new Role(resultSet.getInt(1), resultSet.getString(2));
            } else {
                System.out.println("Role by id is not found.");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        List<Role> rolesList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_ROLES);
            while (resultSet.next()) {
                rolesList.add(new Role(resultSet.getInt(1), resultSet.getString(2)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rolesList;
    }
}
