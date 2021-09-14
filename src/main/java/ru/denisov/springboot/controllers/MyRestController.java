package ru.denisov.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.denisov.springboot.models.User;
import ru.denisov.springboot.services.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("")
public class MyRestController {

    @Autowired
    private UserService userService;

    @GetMapping("api/allUsers")
    public List<User> getAllUsers() {
        return userService.index();
    }

    @GetMapping("api/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("api/newUser")
    public User newUser(@Valid @RequestBody User user) {
        User newUser = user;
        return userService.save(newUser);
    }

    @PutMapping("api/edit")
    public User updateUser(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @DeleteMapping("api/delete/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
    }
}
