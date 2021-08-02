package com.jdbc_project.model;

import java.util.Objects;

public class Job {
    private int id;
    private String name;
    private int salary;

    public Job() {
    }

    public Job(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public Job(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return id == job.id && salary == job.salary && Objects.equals(name, job.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, salary);
    }
}
