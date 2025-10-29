package com.sanucha.BorrowBookAppV2.entitiy;

import jakarta.persistence.*;
import java.time.LocalDate;

// สร้างตาราง
@Entity
@Table(name = "Borrowing")
public class Borrowing {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Integer borrow_id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person_id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book_id;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrow_date;

    @Column(name = "return_date")
    private LocalDate return_date;

    // constructor
    public Borrowing(){

    }

    public Borrowing(Person person_id, Book book_id, LocalDate borrow_date) {
        this.person_id = person_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
    }

    public Borrowing(Integer borrow_id, Person person_id, Book book_id, LocalDate borrow_date, LocalDate return_date) {
        this.borrow_id = borrow_id;
        this.person_id = person_id;
        this.book_id = book_id;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
    }

    public Integer getBorrow_id() {
        return borrow_id;
    }

    public void setBorrow_id(Integer borrow_id) {
        this.borrow_id = borrow_id;
    }

    public Person getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Person person_id) {
        this.person_id = person_id;
    }

    public Book getBook_id() {
        return book_id;
    }

    public void setBook_id(Book book_id) {
        this.book_id = book_id;
    }

    public LocalDate getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(LocalDate borrow_date) {
        this.borrow_date = borrow_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    @Override
    public String toString() {
        return "borrow_id : " + borrow_id +
                ", person_id : " + (person_id != null ? person_id.getPerson_id() : null) +
                ", book_id : " + (book_id != null ? book_id.getBook_id() : null) +
                ", borrow_date : " + borrow_date +
                ", return_date : " + return_date;
    }
}
