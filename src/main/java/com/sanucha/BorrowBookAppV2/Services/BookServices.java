package com.sanucha.BorrowBookAppV2.Services;

import com.sanucha.BorrowBookAppV2.Eeception.ResourceNotFoundException;
import com.sanucha.BorrowBookAppV2.entitiy.Book;
import com.sanucha.BorrowBookAppV2.repository.bookRepository.BookRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.borrowingRepository.BorrowingRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.personRepository.PersonRepositoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices  {
    @Autowired
    private BorrowingRepositoryDAO borrowDAO;

    @Autowired
    private BookRepositoryDAO bookDAO;

    @Autowired
    private PersonRepositoryDAO personDAO;

    // นำหนังสือเข้าสู่ระบบ
    public void bookRegister(String book_name, String writer){

        // ตรวจสอบว่าค่าว่างหรือไม่
        if (book_name.isEmpty() || writer.isEmpty()) {
            throw new IllegalStateException("Please enter name and writer.");
        }

        // ตรวจสอบว่ามีอยู่แล้วหรือเปล่า
        List<Book> existingBooks = bookDAO.haveThisBook(book_name, writer);
        if (!existingBooks.isEmpty()) {
            throw new IllegalStateException("This book has already been added to the system.");
        }

        Book book = new Book(writer, book_name);
        bookDAO.saveBook(book);
    }

    // นำหนังสือออกจากระบบ
    public void bookDeregister(Integer book_id){

        // มีหนังสือไหม
        Book book = bookDAO.getBook(book_id);
        if (book == null){
            throw new ResourceNotFoundException("Not Found Book ID : " + book_id);
        }

        // ตรวจสอบว่าหนังที่จะนำออกจากระบบถูกยืมอยู่หรือไม่
        boolean isBorrowed = !(bookDAO.stillBorrowing(book_id).isEmpty());
        if (isBorrowed) {
            throw new IllegalStateException("Cannot remove book. It is currently borrowed.");
        }

        bookDAO.deleteBook(book_id);
    }
    // แสดงรายการหนังสือที่มีอยู่
    public List<Book> showAllBook(){
        return bookDAO.getAllBook();
    }

    // ค้นหา book_id จาก book_id และ writer
    public Integer searchBook_id(String book_name, String writer) {

        List<Book> books = bookDAO.haveThisBook(book_name, writer);
        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Book ID not Found");
        }
        return books.get(0).getBook_id();
    }

}
