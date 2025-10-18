package com.sanucha.BorrowBookApp.controller;

import com.sanucha.BorrowBookApp.entity.Borrowing;
import com.sanucha.BorrowBookApp.repository.BorrowingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;


@Controller
public class BorrowingController {

    @Autowired
    private BorrowingDAO dao;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("borrowing", new Borrowing());
        return "form";
    }

    @PostMapping("/save")
    public String saveBorrowing(@ModelAttribute Borrowing borrowing){
        borrowing.setDate(LocalDate.now());
        dao.save(borrowing);
        return "redirect:/data";
    }

    @GetMapping("/data")
    public String showData(Model model){
        model.addAttribute("borrowings", dao.getAll());
        return "data";
    }
}
