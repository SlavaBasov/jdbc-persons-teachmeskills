package com.jdbc_project.connection;

import com.jdbc_project.dao.PersonDAOImpl;
import com.jdbc_project.dao.RoleDAOImpl;
import com.jdbc_project.model.Person;
import com.jdbc_project.model.Role;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        PersonDAOImpl actionPerson = new PersonDAOImpl();
        RoleDAOImpl actionRole = new RoleDAOImpl();

        Role role1 = new Role("admin");
        Role role2 = new Role("user");

        Person person1 = new Person("Vasya", "Pupkin", 32, actionRole.findById(26));
        Person person2 = new Person("Valery", "Hleb", 45, actionRole.findById(26));
        //actionPerson.add(person1);
        //actionPerson.delete(person1);

        List<Person> personList = actionPerson.findAll();
        for(Person p: personList){
            p.setFirstName("Vasya");
            actionPerson.update(p);
        }















    }
}
