package com.sanucha.BorrowBookApp.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "borrowing")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "b_id")
    private int b_id;

    @Column(name = "p_id")
    private int p_id;

    @Column(name = "name")
    private String name;

    @Column(name = "book")
    private String book;

    @Column(name = "date")
    private LocalDate date;

    public Borrowing(){

    }

    public Borrowing(int p_id, String name, String book, LocalDate date) {
        this.p_id = p_id;
        this.name = name;
        this.book = book;
        this.date = date;
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "b_id=" + b_id +
                ", p_id=" + p_id +
                ", name='" + name + '\'' +
                ", book='" + book + '\'' +
                ", date=" + date +
                '}';
    }
}
