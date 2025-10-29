package com.sanucha.BorrowBookAppV2.entitiy;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer person_id;

    @Column(name = "person_name", nullable = false)
    private String person_name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "score", nullable = false)
    private Integer score = 10;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "person_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Borrowing> borrowings;

    public Person(){

    }

    public Person(String person_name, String password, Integer age) {
        this.person_name = person_name;
        this.password = password;
        this.age = age;
    }

    public Person(Integer person_id, String person_name, Integer age, Integer score, String password, List<Borrowing> borrowings) {
        this.person_id = person_id;
        this.person_name = person_name;
        this.age = age;
        this.score = score;
        this.password = password;
        this.borrowings = borrowings;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Borrowing> getBorrowings() {
        return borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", person_name='" + person_name + '\'' +
                ", age=" + age +
                ", score=" + score +
                ", password='" + password + '\'' +
                ", borrowings=" + borrowings +
                '}';
    }
}
