package ru.denisov.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denisov.springboot.models.Role;
import ru.denisov.springboot.models.User;
import ru.denisov.springboot.services.RoleService;
import ru.denisov.springboot.services.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model, Principal principal) {
        model.addAttribute("users", userService.index());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("principalUser", userService.getUserByUsername(principal.getName()));
        return "admin/index";
    }

    @GetMapping(value = "/user")
    public String user() {
        return "user";
    }


    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult,
                          @RequestParam(value = "select_role", required = false) String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName(role));
        user.setRoles(roles);
        if (bindingResult.hasErrors()) {
            return "admin/index";
        }

        userService.saveUser(user);
        return "redirect:/admin/";
    }


    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(required = false, value = "select_role") String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName(role));
        user.setRoles(roles);
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
