package ru.denisov.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.denisov.springboot.models.Role;
import ru.denisov.springboot.models.User;
import ru.denisov.springboot.services.RoleService;
import ru.denisov.springboot.services.UserServiceImp;


import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImp userServiceImp;

    @Autowired
    private RoleService roleService;

    @Autowired
    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userServiceImp.index());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("user",userServiceImp.show(id));
        return "admin/show";
    }


    @GetMapping("/new")
    public String createUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("role", roleService.getRoles());
        return "admin/new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user,
                          @RequestParam(value = "select_role", required = false) String[] roles) {
        User userFromDb = userServiceImp.getUserByUsername(user.getUsername());
        if (userFromDb!=null) {
            return "/admin/duplicate";
        }
        Set<Role> role = new HashSet<>();
        role.add(roleService.getRoles().get(1));
        for (String s : roles) {
            if (s.equals("ROLE_ADMIN")) {
                role.add(roleService.getRoles().get(0));
            }
        }
        user.setRoles(role);
        userServiceImp.save(user);
        return "redirect:/admin/";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userServiceImp.show(id));
        model.addAttribute("role",roleService.getRoles());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user")@Valid User user,
                         BindingResult bindingResult, @PathVariable("id") int id,
                         @RequestParam(value = "select_role", required = false) String[] roles){
        if (bindingResult.hasErrors()){

            return "admin/edit";
        }
        Set<Role> role = new HashSet<>();
        role.add(roleService.getRoles().get(1));
        for (String s : roles) {
            if (s.equals("ROLE_ADMIN")) {
                role.add(roleService.getRoles().get(0));
            }
        }
        user.setRoles(role);
        userServiceImp.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/admin";
    }
}
