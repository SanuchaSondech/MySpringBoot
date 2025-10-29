package com.sanucha.BorrowBookAppV2;

import com.sanucha.BorrowBookAppV2.Eeception.ResourceNotFoundException;
import com.sanucha.BorrowBookAppV2.Services.BookServices;
import com.sanucha.BorrowBookAppV2.Services.Borrowingservices;
import com.sanucha.BorrowBookAppV2.Services.PersonServices;
import com.sanucha.BorrowBookAppV2.entitiy.Book;
import com.sanucha.BorrowBookAppV2.entitiy.Borrowing;
import com.sanucha.BorrowBookAppV2.repository.bookRepository.BookRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.borrowingRepository.BorrowingRepositoryDAO;
import com.sanucha.BorrowBookAppV2.repository.personRepository.PersonRepositoryDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BorrowBookAppV2Application {

    public static void main(String[] args) {
        SpringApplication.run(BorrowBookAppV2Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(Borrowingservices borrowServices, BookServices bookServices, PersonServices personServices) {
        return args -> {
            System.out.println("Enter ' ? ' to All function");
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.print(">> ");
                String command = input.nextLine().toLowerCase();
                System.out.println("Processing...");

                switch (command) {

                    // ---------- เมนูหลัก ----------
                    case "?":
                        System.out.println("Available commands:");
                        System.out.println("---- Book Commands ----");
                        System.out.println("book.save        - Add a new book");
                        System.out.println("book.delete      - Delete a book by ID");
                        System.out.println("book.showall     - Show all books");
                        System.out.println("book.searchid    - Search book ID by name and writer");
                        System.out.println("---- Person Commands ----");
                        System.out.println("person.save      - Register a new person");
                        System.out.println("person.delete    - Deregister a person");
                        System.out.println("person.change    - Change person's name");
                        System.out.println("person.show      - Show all person's borrowings");
                        System.out.println("person.searchid  - search person ID");
                        System.out.println("---- Borrow Commands ----");
                        System.out.println("borrow.save      - Borrow a book");
                        System.out.println("borrow.return    - Return a book");
                        System.out.println("borrow.showlate  - Show all late borrowings");
                        System.out.println("exit             - Exit program");
                        break;

                    // ---------- BOOK ----------
                    case "book.save":
                        bookRegisterCommand(bookServices, input);
                        break;

                    case "book.delete":
                        deregisterCommand(bookServices, input);
                        break;

                    case "book.showall":
                        showAllBookCommand(bookServices);
                        break;

                    case "book.searchid":
                        searchBook_idCommand(bookServices, input);
                        break;

                    // ---------- PERSON ----------
                    case "person.save":
                        personRegisterCommand(personServices, input);
                        break;

                    case "person.delete":
                        personDeregisterCommand(personServices, input);
                        break;

                    case "person.change":
                        changePerson_name(personServices, input);
                        break;

                    case "person.show" :
                        myBorrowingCommand(personServices, input);
                        break;

                    case "person.searchid":
                        searchPerson_idCommand(personServices, input);
                        break;

                    // ---------- BORROW ----------
                    case "borrow.save":
                        borrowBookCommand(borrowServices, input);
                        break;

                    case "borrow.return":
                        returnBookCommand(borrowServices, input);
                        break;

                    case "borrow.showlate":
                        showAllBorrowLateCommand(borrowServices);
                        break;

                    // ---------- EXIT ----------
                    case "exit":
                        System.out.println("Exiting program...");
                        System.exit(0);
                        break;

                    // ---------- DEFAULT ----------
                    default:
                        System.out.println("Invalid command. Enter '?' to see available commands.");
                        break;
                }
            }

        };
    }

    // --------------------Book command--------------------

    // เพิ่มหนังสือ
    public void bookRegisterCommand(BookServices bookServices, Scanner input) {
        try {
            System.out.print("Enter Book Name: ");
            String book_name = input.nextLine();

            System.out.print("Enter Writer Name: ");
            String writer = input.nextLine();

            bookServices.bookRegister(book_name, writer);

            // เมื่อรันผ่านให้แสดง
            Integer book_id = bookServices.searchBook_id(book_name, writer);
            String completeText = String.format("----book_id : %d----\nbook_name : %s\nwriter : %s\n saved",
                    book_id, book_name, writer);
            System.out.println(completeText);
        // หนังสือเล่มนี้มีอยู่ในระบบแล้ว
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    // ลบหนังสือ
    public void deregisterCommand(BookServices bookServices, Scanner input) {
        try {
            // input
            System.out.print("Enter Book ID : ");
            String stringBook_id = input.nextLine();
            Integer book_id = Integer.parseInt(stringBook_id);

            bookServices.bookDeregister(book_id);

            // เมื่อรันผ่านให้แสดง
            String completeText = String.format("Book ID : %d deleted", book_id);
            System.out.println(completeText);

            // ที่รันไม่ผ่าน
        // NumberFormatException
        } catch (NumberFormatException e) {
            System.out.println("Please Enter Number Only");
        }
        // ResourceNotFoundException
        catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
        // IllegalStateException
        catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    // ค้นหา id หนังสือ จากชื่อหนังสือและชื่อผู้แต่ง
    public void searchBook_idCommand(BookServices bookServices, Scanner input) {
        try {
            // input
            System.out.print("Enter Book Name: ");
            String book_name = input.nextLine();

            System.out.print("Enter Writer: ");
            String writer = input.nextLine();

            // เรียกใช้ Services
            Integer book_id = bookServices.searchBook_id(book_name, writer);

            // เมื่อรันผ่านให้แสดง
            String completeText = String.format("Book ID is %d", book_id);
            System.out.println(completeText);

        // ถ้าไม่มี
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // แสดงหนังสือทั้งหมด
    public void showAllBookCommand(BookServices bookServices) {
        List<Book> books = bookServices.showAllBook();
        System.out.println("#All books#");
            for (Book book : books) {
                System.out.println(book.toString());
                System.out.println();
            }
    }


    //--------------------Person command--------------------
    // ลงทะเบียนผู้ใช้ใหม่
    public void personRegisterCommand(PersonServices personServices, Scanner input) {
        try {
            // input
            System.out.print("Enter Person Name : ");
            String person_name = input.nextLine();

            System.out.print("Enter Person Password : ");
            String password = input.nextLine();

            System.out.print("Enter Age : ");
            String stringAge = input.nextLine();
            Integer age = Integer.parseInt(stringAge);

            // เรียกใช้ Services
            personServices.personRegister(person_name, password, age);

            // เมื่อรันผ่านให้แสดง
            Integer person_id = personServices.searchPerson_id(person_name, password, age);
            String completeText = String.format("----person_id : %d----\nperson_name : %s\npassword : %s\nage : %d\n saved"
                    , person_id, person_name, password, age);
            System.out.println(completeText);

        // รันไม่ผ่าน
        } catch (NumberFormatException e) {
            System.out.println("Please Enter Number Only");
       // Age connot more than 999 year
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    // ยกเลิกสมาชิก
    public void personDeregisterCommand(PersonServices personServices, Scanner input) {
        try {
            // input
            System.out.print("Enter Person ID: ");
            String stringPerson_id = input.nextLine();
            Integer person_id = Integer.parseInt(stringPerson_id);

            System.out.print("Enter Person Password : ");
            String password = input.nextLine();

            // เรียกใช้ Services
            personServices.personDeregister(person_id, password);

            // เมื่อรันผ่านให้แสดง
            String completeText = String.format("Person ID : %d deleted", person_id);
            System.out.println(completeText);

        // รันไม่ผ่าน
        } catch (NumberFormatException e) {
            System.out.println("Please Enter Number Only");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }

    }

    // ค้นหา person_id จากชื่อ รหัสผ่าน และอายุ
    public void searchPerson_idCommand(PersonServices personServices, Scanner input) {
        try {
            // input
            System.out.print("Enter Person Name : ");
            String person_name = input.nextLine();

            System.out.print("Enter Password : ");
            String password = input.nextLine();

            System.out.print("Enter Age: ");
            String stringAge = input.nextLine();
            Integer age = Integer.parseInt(stringAge);

            // เรียกใช้ Services
            Integer person_id = personServices.searchPerson_id(person_name, password, age);

            // เมื่อรันผ่านให้แสดง
            String completeText = String.format("Person ID is %d", person_id);
            System.out.println(completeText);

            // ถ้าไม่มี
        }catch (NumberFormatException e){
            System.out.println("Please Enter Number Only");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    // เปลี่ยนชื่อ
    public void changePerson_name(PersonServices personServices, Scanner input){
        try{
            // input
            System.out.print("Enter Person ID: ");
            String stringPerson_id = input.nextLine();
            Integer person_id = Integer.parseInt(stringPerson_id);

            System.out.print("Enter Person Password : ");
            String password = input.nextLine();

            System.out.print("Enter New name : ");
            String newName = input.nextLine();

            // เรียกใช้ Services
            personServices.changePerson_name(person_id, password, newName);

            // เมื่อรันผ่านให้แสดง
            String completeText = String.format("Person ID %d changed name to %s", person_id, newName);
            System.out.println(completeText);

        // รันไม่ผ่าน
        }catch (NumberFormatException e){
            System.out.println("Please enter number only");
        }catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    // เช็คหนังสือที่กำลังยืม
    public void myBorrowingCommand(PersonServices personServices, Scanner input){
        try {
            // input
            System.out.print("Enter Person ID: ");
            String stringPerson_id = input.nextLine();
            Integer person_id = Integer.parseInt(stringPerson_id);

            System.out.print("Enter Person Password : ");
            String password = input.nextLine();

            // เรียกใช้ Services
            List<Borrowing> borrowings  = personServices.myBorrowing(person_id, password);

            // เมื่อรันผ่านให้แสดง
            System.out.println("this is your borrow list.");
            for (Borrowing borrowing : borrowings ){
                System.out.println(borrowing);
                System.out.println();
            }

        }catch (NumberFormatException e){
            System.out.println("Please enter number only");
        }catch (ResourceNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    //--------------------Borrowing Command------------------------
    //ยืม
    public void borrowBookCommand (Borrowingservices borrowingservices, Scanner input){
        try {
            // input
            System.out.print("Enter Person ID: ");
            String stringPerson_id = input.nextLine();
            Integer person_id = Integer.parseInt(stringPerson_id);

            System.out.print("Enter Book ID: ");
            String stringBook_id = input.nextLine();
            Integer book_id = Integer.parseInt(stringBook_id);

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            LocalDate borrow_date = LocalDate.now();

            // เรียกใช้ services
            borrowingservices.borrowbook(person_id, book_id, password, borrow_date);

            String completeText = String.format("Person ID: %d has borrowed Book ID: %d", person_id, book_id);
            System.out.println(completeText);

        } catch (NumberFormatException e) {
            System.out.println("Please enter number only");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    //คืน
    public void returnBookCommand(Borrowingservices borrowingservices, Scanner input){
        try {
            // input
            System.out.print("Enter Person ID: ");
            String stringPerson_id = input.nextLine();
            Integer person_id = Integer.parseInt(stringPerson_id);

            System.out.print("Enter Borrowing ID: ");
            String stringBorrow_id = input.nextLine();
            Integer borrow_id = Integer.parseInt(stringBorrow_id);

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            LocalDate return_date = LocalDate.now();

            // เรียกใช้ services
            borrowingservices.returnbook(person_id, borrow_id, password, return_date);
            String completeText = String.format("Person ID: %d has returned Borrowing ID: %d", person_id, borrow_id);
            System.out.println(completeText);


        } catch (NumberFormatException e) {
            System.out.println("Please enter number only");
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    //เเสดงรายการที่คืนช้า
    public void showAllBorrowLateCommand(Borrowingservices borrowingservices) {
        try {
            List<Borrowing> lateList = borrowingservices.showAllBorrowLate();

            if (lateList.isEmpty()) {
                System.out.println("No late borrowings found.");
                return;
            }

            for (Borrowing b : lateList) {
                System.out.printf("Borrowing ID: %d | Person: %s | Book: %s | Borrow Date: %s\n",
                        b.getBorrow_id(),
                        b.getPerson_id().getPerson_name(),
                        b.getBook_id().getBook_name(),
                        b.getBorrow_date());
            }
        } catch (Exception e) {
            System.out.println("Error retrieving late borrowings: ");
        }
    }
}