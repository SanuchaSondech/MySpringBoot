package com.sanucha.BorrowBookApp.repository;

import com.sanucha.BorrowBookApp.entity.Borrowing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowingRepository implements BorrowingDAO {

    private EntityManager entityManager;

    @Autowired
    public BorrowingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Borrowing borrowing) {
        entityManager.persist(borrowing);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Borrowing borrowing = entityManager.find(Borrowing.class, id);
        entityManager.remove(borrowing);
    }

    @Override
    @Transactional
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM Borrowing").executeUpdate();
    }

    @Override
    public Borrowing get(Integer id) {
        return entityManager.find(Borrowing.class, id);
    }

    @Override
    public List<Borrowing> getAll() {
        TypedQuery<Borrowing> query = entityManager.createQuery("FROM Borrowing", Borrowing.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void update(Borrowing borrowing) {
        entityManager.merge(borrowing);
    }
}

