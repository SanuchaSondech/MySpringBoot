package com.sanucha.BorrowBookAppV2.repository.personRepository;

import com.sanucha.BorrowBookAppV2.entitiy.Borrowing;
import com.sanucha.BorrowBookAppV2.entitiy.Person;

import java.util.List;

public interface PersonRepositoryDAO {
    void savePerson(Person person);

    void deletePerson(Integer person_id);

    void deleteAllPerson();

    Person getPerson(Integer person_id);

    List<Person> getAllPerson();

    void updatePerson(Person person);

    List<Borrowing> isBorrowing(Integer person_id);

    Person findPerson_id(String person_name, String password, Integer age);
}
