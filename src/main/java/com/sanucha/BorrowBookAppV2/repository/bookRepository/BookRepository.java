package com.sanucha.BorrowBookAppV2.repository.bookRepository;

import com.sanucha.BorrowBookAppV2.entitiy.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository implements BookRepositoryDAO {

    private final EntityManager entityManager;

    @Autowired
    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void saveBook(Book book) {
        entityManager.persist(book);
    }

    @Override
    @Transactional
    public void deleteBook(Integer book_id) {
        Book book = entityManager.find(Book.class, book_id);
        entityManager.remove(book);

    }

    @Override
    @Transactional
    public void deleteAllBook() {
        entityManager.createQuery("DELETE FROM Book").executeUpdate();
    }

    @Override
    public Book getBook(Integer book_id) {
        return entityManager.find(Book.class, book_id);
    }

    @Override
    public List<Book> getAllBook() {
        TypedQuery<Book> query = entityManager.createQuery("FROM Book", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        entityManager.merge(book);
    }

    @Override
    public List<Book> haveThisBook(String book_name, String writer) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.writer = :writer AND b.book_name = :bookName"
                ,Book.class);
        query.setParameter("writer", writer);
        query.setParameter("bookName", book_name);
        return query.getResultList();

    }

    // หนังสือ id นี้ถูกยืมอยู่ไหม
    @Override
    public List<Book> stillBorrowing(Integer book_id) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b.book_id FROM Borrowing b WHERE b.book_id.book_id = :bookId AND b.return_date IS NULL",
                Book.class);
        query.setParameter("bookId", book_id);
        return query.getResultList();
    }

}