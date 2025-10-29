package com.sanucha.BorrowBookAppV2.entitiy;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer book_id;

    @Column(name = "book_name", nullable = false)
    private String book_name;

    @Column(name = "writer", nullable = false)
    private String writer;

    @OneToMany(mappedBy = "book_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Borrowing> borrowings;

    public Book(){

    }

    public Book(String writer, String book_name) {
        this.writer = writer;
        this.book_name = book_name;
    }

    public Book(Integer book_id, String book_name, String writer, List<Borrowing> borrowings) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.writer = writer;
        this.borrowings = borrowings;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public String toString() {
        return String.format("----book_id : %d----\nbook_name : %s\nwriter : %s",
                book_id, book_name, writer);
    }
}
