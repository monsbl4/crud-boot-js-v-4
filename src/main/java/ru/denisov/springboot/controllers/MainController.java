package ru.denisov.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.denisov.springboot.models.User;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping(value = "/admin")
    public String showAllUsers() {
        return "admin";
    }

    @GetMapping(value = "/user")
    public String user() {
        return "user";
    }

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(value = "select_roles", required = false) String[] role) {

        return "redirect:/admin";
    }
}