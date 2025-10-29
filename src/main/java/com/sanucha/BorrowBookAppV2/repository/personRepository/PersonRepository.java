package com.sanucha.BorrowBookAppV2.repository.personRepository;

import com.sanucha.BorrowBookAppV2.entitiy.Borrowing;
import com.sanucha.BorrowBookAppV2.entitiy.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository implements PersonRepositoryDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void savePerson(Person person) {
        entityManager.persist(person);
    }

    @Override
    @Transactional
    public void deletePerson(Integer person_id) {
        Person person = entityManager.find(Person.class, person_id);
        entityManager.remove(person);
    }

    @Override
    @Transactional
    public void deleteAllPerson() {
        entityManager.createQuery("DELETE FROM Person").executeUpdate();
    }

    @Override
    public Person getPerson(Integer person_id) {
        return entityManager.find(Person.class, person_id);
    }

    @Override
    public List<Person> getAllPerson() {
        TypedQuery<Person> query = entityManager.createQuery("FROM Person", Person.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updatePerson(Person person) {
        entityManager.merge(person);
    }

    // แสดงรายการ Borrowing ที่ person_id ยืมอยู่และยังไม่มี return_date
    @Override
    @Transactional
    public List<Borrowing> isBorrowing(Integer person_id) {
        TypedQuery<Borrowing> query = entityManager.createQuery(
                "SELECT b FROM Borrowing b WHERE b.person_id.person_id = :personId AND b.return_date IS NULL",
                Borrowing.class
        );
        query.setParameter("personId", person_id);
        return query.getResultList();
    }

    // ค้นหาข้อมูลของคนคนนั้นด้วย person_name และ password
    @Transactional
    @Override
    public Person findPerson_id(String person_name, String password, Integer age) {
        TypedQuery<Person> query = entityManager.createQuery(
                "SELECT p FROM Person p WHERE p.person_name = :personName AND p.password = :password AND p.age = :age",
                Person.class
        );
        query.setParameter("personName", person_name);
        query.setParameter("password", password);
        query.setParameter("age", age);

        List<Person> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }


}