package com.sanucha.BorrowBookAppV2.repository.bookRepository;

import com.sanucha.BorrowBookAppV2.entitiy.Book;

import java.util.List;

public interface BookRepositoryDAO {

    void saveBook(Book book);

    void deleteBook(Integer book_id);

    void deleteAllBook();

    Book getBook(Integer book_id);

    List<Book> getAllBook();

    void updateBook(Book book);

    // มีหนังสืออยู่ไหม
    List<Book> haveThisBook(String book_name, String writer);

    // หนังสือถูกยืมอยู่ไหม
    List<Book> stillBorrowing(Integer book_id);
}
