package com.sanucha.BorrowBookAppV2.Services;

import com.sanucha.BorrowBookAppV2.Eeception.ResourceNotFoundException;
import com.sanucha.BorrowBookAppV2.entitiy.Book;
import com.sanucha.BorrowBookAppV2.entitiy.Borrowing;
import com.sanucha.BorrowBookAppV2.entitiy.Person;
import com.sanucha.BorrowBookAppV2.repository.bookRepository.BookRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.borrowingRepository.BorrowingRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.personRepository.PersonRepository;
import com.sanucha.BorrowBookAppV2.repository.personRepository.PersonRepositoryDAO;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.List;
@Service
public class Borrowingservices {

    @Autowired
    private BorrowingRepositoryDAO borrowDAO;

    @Autowired
    private BookRepositoryDAO bookDAO;

    @Autowired
    private PersonRepositoryDAO personDAO;

    //ยืม
    public void borrowbook(Integer person_id, Integer book_id, String password, LocalDate borrow_date) {

        // มี person_id จริงไหม
        Person person = personDAO.getPerson(person_id);
        if (person == null) {
            throw new ResourceNotFoundException("Not Found Person ID : " + person_id);
        }

        // ตรวจรหัสผ่านว่าไม่ถูกต้อง
        String correctPass =  person.getPassword();
        if (!(password.equals(correctPass)) ){
            throw new IllegalStateException("Password is not coorrect ");
        }

        //เช็คว่าถูกแบนไหมจากอายุ
        Integer personAge = person.getAge();
        if (personAge < 0){
            throw new IllegalStateException("You are banned and cannot borrow");
        }

        // มี book_Id จริงไหม
        Book  book = bookDAO.getBook(book_id);
        if (book == null){
            throw new ResourceNotFoundException("Not Foud Book ID : "+ book_id);
        }

        // นับจำนวนหนังสือที่ยังไม่คืน
        long countBorrowNotReturn = borrowDAO.getBorrowNotReturn(person_id).stream().count();

        // ถ้ายืมครบแล้ว ไม่ให้ยืมอีก
        if (countBorrowNotReturn >= 3){
            throw new IllegalStateException("Con not Borrow more than 3 book");
        }

        Borrowing borrowing = new Borrowing(person, book, borrow_date);
        borrowDAO.saveBorrowing(borrowing);
    }

    // คืน
    public void returnbook(Integer person_id, Integer borrow_id, String password, LocalDate return_date){

        // มี person_id จริงไหม
        Person person = personDAO.getPerson(person_id);
        if (person == null) {
            throw new ResourceNotFoundException("Not Found Person ID : " + person_id);
        }

        // มี borrow_id จริงไหม
        Borrowing borrowing = borrowDAO.getBorrowing(borrow_id);
        if (borrowing == null){
            throw new ResourceNotFoundException("Not Foud Borrow ID : "+ borrow_id);
        }

        // รหัสผ่านถูกไหม
        String correctPass =  person.getPassword();
        if (!Objects.equals(password, correctPass)){
            throw new IllegalStateException("Password is not coorrect ");
        }

        // person_id ตรงกับ person_id ใน borrowing นี้ไหม
        if (!borrowing.getPerson_id().getPerson_id().equals(person_id)) {
            throw new IllegalStateException("This borrowing does not belong to you");
        }

        //ตรวจสอบ คะแนน(+-)
        int score = person.getScore();
        boolean isLate = borrowDAO.isLate(borrow_id, return_date);

        if (isLate) { // คืนช้า
            if (score > 0) {
                person.setScore(score - 1);
                System.out.println("Your Score -1");
            } else {
                person.setAge(-1000); // แบนโดยใช้ age
                System.out.println("You are banned from borrowing books.");
            }
        } else {
            if (score < 10) {
                person.setScore(score + 1);
            } else {
                System.out.println("Your Score is Full");
            }
        }
        personDAO.updatePerson(person);
        String textScore = String.format("Your Score is %d", person.getScore());
        System.out.println(textScore);
        borrowing.setReturn_date(return_date);
        borrowDAO.updateBorrowing(borrowing);
    }
    // ดูรายการทั้งหมดที่สาย
    public List<Borrowing> showAllBorrowLate() {
        LocalDate now_date = LocalDate.now();
        return borrowDAO.getAllBorrowLate();
    }

}