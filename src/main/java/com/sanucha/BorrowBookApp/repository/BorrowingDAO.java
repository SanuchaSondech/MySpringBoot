package com.sanucha.BorrowBookApp.repository;

import com.sanucha.BorrowBookApp.entity.Borrowing;

import java.util.List;

public interface BorrowingDAO {
    // บันทีก
    void save(Borrowing borrowing);

    // ลบ
    void delete(Integer id);

    // ลบทั้งหมด
    void deleteAll();

    // ดู 1 แถว
    Borrowing get(Integer id);

    // ดูทั้งหมด
    List<Borrowing> getAll();

    // แก้ไขข้อมูล
    void update(Borrowing borrowing);

}
