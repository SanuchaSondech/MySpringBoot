package com.sanucha.crudapp.repository;

import com.sanucha.crudapp.entity.Person;

import java.util.List;

public interface PersonDIO {
    void save(Person person);
    void delete(Integer id);
    Person get(Integer id);
    List<Person> getAll();
    void update(Person person);
}
