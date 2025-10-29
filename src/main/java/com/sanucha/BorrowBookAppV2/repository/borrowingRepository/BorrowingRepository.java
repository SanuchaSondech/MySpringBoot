package com.sanucha.BorrowBookAppV2.repository.borrowingRepository;

import com.sanucha.BorrowBookAppV2.entitiy.Borrowing;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class BorrowingRepository implements BorrowingRepositoryDAO {

    private EntityManager entityManager;

    // สร้าง constructor
    @Autowired
    public BorrowingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // บันทึกข้อมูล
    @Override
    @Transactional
    public void saveBorrowing(Borrowing borrowing) {
        entityManager.persist(borrowing);
    }

    // ลบข้อมูล
    @Override
    @Transactional
    public void deleteBorrowing(Integer borrow_id) {
        Borrowing borrowing = entityManager.find(Borrowing.class, borrow_id);
        entityManager.remove(borrowing);
    }

    // ลบทุกข้อมูลที่มี
    @Override
    @Transactional
    public void deleteAllBorrowing() {
        entityManager.createQuery("DELETE FROM Borrowing").executeUpdate();
    }

    // ดูข้อมูล 1 แถว
    @Override
    public Borrowing getBorrowing(Integer borrow_id) {
        return entityManager.find(Borrowing.class, borrow_id);
    }

    // ดูข้อมูลทุกแถว
    @Override
    public List<Borrowing> getAllBorrowing() {
        TypedQuery<Borrowing> query = entityManager.createQuery("FROM Borrowing", Borrowing.class);
        return query.getResultList();
    }

    // เปลี่ยนข้อมูล 1 แถว
    @Override
    @Transactional
    public void updateBorrowing(Borrowing borrowing) {
        entityManager.merge(borrowing);
    }

    // อ่านค่า List ของ แถว Borrowing ที่ไม่มี return_date ของ person_id นั้น
    @Override
    public List<Borrowing> getBorrowNotReturn(Integer person_id) {
        TypedQuery<Borrowing> query = entityManager.createQuery(
                "SELECT b FROM Borrowing b WHERE b.return_date IS NULL AND b.person_id.person_id = :personId",
                Borrowing.class);
        query.setParameter("personId", person_id);
        return query.getResultList();
    }

    // boolean เช็คว่าสายไหม
    @Override
    public boolean isLate(Integer borrow_id, LocalDate return_date) {
        Borrowing borrowing = entityManager.find(Borrowing.class, borrow_id);
        long countDays = ChronoUnit.DAYS.between(borrowing.getBorrow_date(), return_date);
        return (countDays > 30);
    }

    // ดูรายการที่ยังไม่คืนมากกว่า 30 วันแล้ว
    @Override
    public List<Borrowing> getAllBorrowLate() {
        String jpql = "SELECT b FROM Borrowing b " +
                "WHERE DATEDIFF(CURDATE(), borrow_date) > 30 AND return_date IS NULL";
        TypedQuery<Borrowing> q = entityManager.createQuery(jpql, Borrowing.class);
        return q.getResultList();
    }
}