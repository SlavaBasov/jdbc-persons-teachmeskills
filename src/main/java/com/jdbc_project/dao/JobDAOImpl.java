package com.jdbc_project.dao;

import com.jdbc_project.connection.DBConnection;
import com.jdbc_project.model.Job;
import com.jdbc_project.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAOImpl implements BaseDAO<Job>{
    final static String INSERT_JOB = "INSERT job(name, salary) VALUES (?, ?)";
    final static String DELETE_JOB = "DELETE FROM job WHERE id = ? AND name = ? AND salary = ?";
    final static String FIND_JOB = "SELECT * FROM job WHERE ID = ?";
    final static String FIND_ALL_JOBS = "SELECT * FROM job";
    final static String UPDATE_JOB = "UPDATE job SET name = ?, salary = ? WHERE Id = ?";

    @Override
    public boolean add(Job job) {
        int rows = 0;
        Job jobFound = new Job();
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_JOB, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, job.getName());
            preparedStatement.setInt(2, job.getSalary());

            rows = preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                jobFound = findById(generatedKeys.getInt(1));
                System.out.printf("Job %s is successfully created with id = %d, salary = %d\n", jobFound.getName(),
                        jobFound.getId(), jobFound.getSalary());

            } else throw new SQLException("Creating job failed, no ID obtained.");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return rows != 0;
    }

    @Override
    public boolean update(Job job) {
        int id = job.getId();
        Job oldJob = findById(id);
        if (id != 0) {
            if(job.equals(oldJob)) System.out.printf("Job '%s' (id = %d) does not require updating\n", job.getName(), job.getId());
            else {
                try (Connection connection = DBConnection.getConnection()) {
                    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_JOB);
                    preparedStatement.setString(1, job.getName());
                    preparedStatement.setInt(2, job.getSalary());
                    preparedStatement.setInt(3, job.getId());
                    preparedStatement.executeUpdate();


                    System.out.printf("Job '%s' (id = %d) is successfully updated. New role is: \n",
                            oldJob.getName(), oldJob.getId());
                    System.out.println(job);

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
    public boolean delete(Job job) {
        int rows = 0;

        if (job.getId() == 0) System.out.println("Job is not found");
        else {

            try (Connection connection = DBConnection.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_JOB);
                preparedStatement.setInt(1, job.getId());
                preparedStatement.setString(2, job.getName());
                preparedStatement.setInt(3, job.getSalary());
                rows = preparedStatement.executeUpdate();

                System.out.printf("Role %s with id = %d is successfully deleted\n", job.getName(), job.getId());


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
        Job job = findById(id);
        delete(job);
        return rows!=0;
    }

    @Override
    public void deleteAll() {
        List<Job> jobsList = findAll();
        for (Job job : jobsList) {
            delete(job);
        }
    }

    @Override
    public Job findById(int id) {
        Job job = new Job();
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_JOB);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                job = new Job(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
            } else {
                System.out.println("Job is not found.");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return job;
    }

    @Override
    public List<Job> findAll() {
        List<Job> jobsList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_JOBS);
            while (resultSet.next()) {
                jobsList.add(new Job(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getInt(3)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jobsList;
    }
}
