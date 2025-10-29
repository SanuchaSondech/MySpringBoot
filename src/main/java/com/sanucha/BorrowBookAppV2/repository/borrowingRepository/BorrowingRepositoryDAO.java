package com.sanucha.BorrowBookAppV2.repository.borrowingRepository;

import com.sanucha.BorrowBookAppV2.entitiy.Borrowing;

import java.time.LocalDate;
import java.util.List;

public interface BorrowingRepositoryDAO {

    void saveBorrowing(Borrowing borrowing);

    void deleteBorrowing(Integer borrow_id);

    void deleteAllBorrowing();

    Borrowing getBorrowing(Integer borrow_id);

    List<Borrowing> getAllBorrowing();

    void updateBorrowing(Borrowing borrowing);

    List<Borrowing> getBorrowNotReturn(Integer person_id);

    boolean isLate(Integer borrow_id,LocalDate return_date);

    List<Borrowing> getAllBorrowLate();
}
