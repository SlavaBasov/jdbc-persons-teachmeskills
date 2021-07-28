package com.jdbc_project.dao;


import java.util.*;

public interface BaseDAO<T> {
    boolean add(T t);

    boolean delete(T t);

    T findById(int id);

    List<T> findAll();
}

