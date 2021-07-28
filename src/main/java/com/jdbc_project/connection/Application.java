package com.jdbc_project.connection;

import com.jdbc_project.dao.PersonDAOImpl;
import com.jdbc_project.model.Person;

public class Application {
    public static void main(String[] args) {
        PersonDAOImpl personDAO = new PersonDAOImpl();
        Person person = new Person("Vasiliy", "Pupkin", 33);
        Person person1 = personDAO.findById(1);
        System.out.println(person1.toString());
        personDAO.delete(personDAO.findById(1));
    }
}
