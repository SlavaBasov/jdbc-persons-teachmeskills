package com.jdbc_project.dao;

import com.jdbc_project.connection.DBConnection;
import com.jdbc_project.model.Job;
import com.jdbc_project.model.Person;
import com.jdbc_project.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonJobImpl{
    final static String INSERT_PERSONS_JOB = "INSERT persons_job_table(person_id, job_id) VALUES (?, ?)";
    final static String DELETE_PERSONS_JOB = "DELETE FROM persons_job_table WHERE person_id = ? AND job_id = ?";
    final static String FIND_JOBS = "SELECT * FROM persons_job_table WHERE person_id = ?";
    final static String FIND_ALL_JOBS = "SELECT * FROM job";
    final static String UPDATE_JOB = "UPDATE job SET name = ?, salary = ? WHERE Id = ?";

    public boolean add(Person person, Job job){
        int rows = 0;
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERSONS_JOB, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setInt(2, job.getId());

            rows = preparedStatement.executeUpdate();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rows != 0;
    }

    public boolean delete(Person person, Job job){
        int rows = 0;

        if(person.getId() == 0 || job.getId() == 0) System.out.println("Person_Job is not found");
        else {
            try (Connection connection = DBConnection.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PERSONS_JOB);
                preparedStatement.setInt(1, person.getId());
                preparedStatement.setInt(2, job.getId());
                rows = preparedStatement.executeUpdate();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return rows != 0;
    }

    public List<Job> foundByPersonsId(int id){
        List<Job> jobsList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_JOBS);
            while (resultSet.next()) {
                jobsList.add(new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jobsList;
    }
}
