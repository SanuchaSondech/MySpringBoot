package com.sanucha.BorrowBookAppV2.Services;

import com.sanucha.BorrowBookAppV2.Eeception.ResourceNotFoundException;
import com.sanucha.BorrowBookAppV2.entitiy.Book;
import com.sanucha.BorrowBookAppV2.entitiy.Borrowing;
import com.sanucha.BorrowBookAppV2.entitiy.Person;
import com.sanucha.BorrowBookAppV2.repository.bookRepository.BookRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.borrowingRepository.BorrowingRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.personRepository.PersonRepositoryDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service

public class PersonServices {

    @Autowired
    private BorrowingRepositoryDAO borrowDAO;

    @Autowired
    private BookRepositoryDAO bookDAO;

    @Autowired
    private PersonRepositoryDAO personDAO;

    // สมัครสมาชิก
    public void personRegister(String person_name, String password, Integer age){

        // ตรวจสอบว่าค่าว่างหรือไม่
        if (person_name.isEmpty() || password.isEmpty() || age == null) {
            throw new IllegalStateException("Please enter name password and age.");
        }

        // เช็คอายุไม่ควรเกิน 100
        if (age < 0 ||age >= 150){
            throw new IllegalStateException("ํYour age is incorrect ");
        }

        // สร้าง obj มาเก็บและบันทึก
        Person person = new Person(person_name, password, age);
        personDAO.savePerson(person);
    }

    // ยกเลิกสมาชิก
    public void personDeregister(Integer person_id, String password){

        // เช็คว่ามีจริงไหม
        Person person = personDAO.getPerson(person_id);
        if (person == null) {
            throw new ResourceNotFoundException("Not Found Person ID : " + person_id);
        }

        // รหัสผ่านถูกไหม
        String correctPass =  person.getPassword();
        if (!Objects.equals(password, correctPass)){
            throw new IllegalStateException("Password is not correct");
        }

        // เช็คว่ายังมีหนังสือที่ไม่คืนอยู่ไหม
        List<Borrowing> isBorrowing = personDAO.isBorrowing(person_id);
        if (!isBorrowing.isEmpty()){
            throw new IllegalStateException("Cannot deregister because you have unreturned books.");
        }

        // ลบข้อมูล
        personDAO.deletePerson(person_id);
    }

    //เช็คหนังสือที่กำลังยืม
    @Transactional
    public List<Borrowing>myBorrowing(Integer person_id, String password){

        // มี person_id นี้ไหม
        Person person = personDAO.getPerson(person_id);
        if (person == null){
            throw new ResourceNotFoundException("Not Found Person ID : " + person_id);
        }

        // รหัสผ่านถูกไหม
        String correctPass =  person.getPassword();
        if (!Objects.equals(password, correctPass)){
            throw new IllegalStateException("Password is not correct");
        }

        return personDAO.isBorrowing(person_id);

    }

    // เปลี่ยนชื่อผู้ใช้
    public void changePerson_name(Integer person_id, String password, String newName){
        // มี person_id นี้ไหม
        Person person = personDAO.getPerson(person_id);
        if (person == null){
            throw new ResourceNotFoundException("Not Found Person ID : " + person_id);
        }

        // รหัสผ่านถูกไหม
        String correctPass =  person.getPassword();
        if (!Objects.equals(password, correctPass)){
            throw new IllegalStateException("Password is not coorrect ");
        }

        // เปลี่ยนชื่อ
        person.setPerson_name(newName);
        personDAO.updatePerson(person);
    }

    // ค้นหา person_id จาก person_name password และ age
    public Integer searchPerson_id(String person_name, String password, Integer age) {
        // เรียกใช้ personDAO
        Person person = personDAO.findPerson_id(person_name, password, age);
        // ตรวจว่ามีจริงไหม
        if (person == null) {
            throw new ResourceNotFoundException("Person not found");
        }
        // คืนค่าเป็น Integer person_id()
        return person.getPerson_id();
    }

}
