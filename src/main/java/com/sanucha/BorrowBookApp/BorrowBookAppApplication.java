package com.sanucha.BorrowBookApp;

import com.sanucha.BorrowBookApp.entity.Borrowing;
import com.sanucha.BorrowBookApp.repository.BorrowingDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class BorrowBookAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BorrowBookAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BorrowingDAO dao) {
        return runner -> {
            System.out.println("Enter ' ? ' to All function");
            Scanner input = new Scanner(System.in);
            while (true) {
                System.out.print(">> ");
                String command = input.nextLine().toLowerCase();
                System.out.println("Processing...");

                switch (command) {
                    case "?":
                        System.out.println("All function");
                        System.out.println("data");
                        System.out.println("add");
                        System.out.println("delete");
                        System.out.println("update");
                        System.out.println("clear");
                        System.out.println("get");
                        System.out.println("close");
                        break;
                    case "data":
                        getAllData(dao);
                        break;
                    case "add":
                        saveData(dao, input);
                        break;
                    case "delete":
                        deleteData(dao, input);
                        break;
                    case "clear":
                        System.out.println("Enter 'confirm' to delete all data");
                        String confirm = input.nextLine().toLowerCase();
                        if (confirm.equals("confirm")) {
                            deleteAllData(dao);
                            break;
                        } else {
                            System.out.println("Cancel");
                            break;
                        }
                    case "update":
                        upDateData(dao, input);
                        break;
                    case "get":
                        getData(dao, input);
                        break;
                    case "close":
                        System.out.println("Program has closed");
                        return;
                    default:
                        System.out.println("Unknown : " + command);
                }
            }
        };
    }

    public void saveData(BorrowingDAO dao, Scanner input) {
        System.out.print("Enter person ID: ");
        int p_id = input.nextInt();
        input.nextLine();

        System.out.print("Enter name: ");
        String name = input.nextLine();

        System.out.print("Enter book name: ");
        String book = input.nextLine();

        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateStr = input.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        Borrowing obj = new Borrowing(p_id, name, book, date);
        dao.save(obj);
        String compeleText = String.format("personId : %d\nname : %s\nbook : %s\ndate : %s\nsaved", p_id, name, book, dateStr);
        System.out.println(compeleText);
    }

    public void deleteData(BorrowingDAO dao, Scanner input) {
        System.out.print("Enter borrowing ID: ");
        int b_id = input.nextInt();
        input.nextLine();

        dao.delete(b_id);
        String textComplate = String.format("%d delete  Complete", b_id);
        System.out.println(textComplate);
    }


    public void deleteAllData(BorrowingDAO dao) {
        List<Borrowing> data = dao.getAll();
        for (Borrowing borrowing : data) {
            System.out.println(borrowing);
            dao.delete(borrowing.getB_id());
        }
        System.out.println("Delete all data Complate");
    }

    public void getData(BorrowingDAO dao, Scanner input) {
        System.out.print("Enter borrowing ID: ");
        int b_id = input.nextInt();
        input.nextLine();

        Borrowing borrowing = dao.get(b_id);
        if (borrowing == null) {
            System.out.println("Unknown ID");
            return;
        }

        System.out.printf("ID: %d\nPerson ID: %d\nName: %s\nBook: %s\nDate: %s\n",
                borrowing.getB_id(),
                borrowing.getP_id(),
                borrowing.getName(),
                borrowing.getBook(),
                borrowing.getDate());
    }

    public void getAllData(BorrowingDAO dao) {
        List<Borrowing> data = dao.getAll();
        for (Borrowing borrowing : data) {
            System.out.println(borrowing);
        }
    }

    public void upDateData(BorrowingDAO dao, Scanner input) {
        System.out.print("Enter borrowing ID: ");
        int p_id = input.nextInt();
        input.nextLine();
        Borrowing borrowing = dao.get(p_id);
        if (borrowing == null) {
            System.out.println("Unknown ID");
            return;
        }
        String oldBook = borrowing.getBook();
        System.out.print("Enter new book: ");
        String newBook = input.nextLine();
        borrowing.setBook(newBook);
        dao.update(borrowing);
        String completeText = String.format("%s has returned\n%s is borrowing", oldBook, newBook);
        System.out.println(completeText);
    }
}