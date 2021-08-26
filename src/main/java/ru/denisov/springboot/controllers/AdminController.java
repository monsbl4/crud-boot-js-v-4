package ru.denisov.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.denisov.springboot.models.Role;
import ru.denisov.springboot.models.User;
import ru.denisov.springboot.services.RoleService;
import ru.denisov.springboot.services.UserServiceImp;

import javax.validation.Valid;
import java.security.Principal;
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
    public String index(Model model, Principal principal) {
        model.addAttribute("users", userServiceImp.index());
        model.addAttribute("roles", roleService.getRoles());
        model.addAttribute("principalUser", userServiceImp.getUserByUsername(principal.getName()));
        return "admin/index";
    }


    @PostMapping()
    public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult,
                          @RequestParam(value = "select_role", required = false) String role) {
        Set<Role>roles = new HashSet<>();
        roles.add(roleService.getRoleByName(role));
        user.setRoles(roles);
        if (bindingResult.hasErrors()){
            return "admin/index";
        }

        userServiceImp.saveUser(user);
        return "redirect:/admin/";
    }


    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(required = false, value = "select_role") String role){
        Set<Role>roles = new HashSet<>();
        roles.add(roleService.getRoleByName(role));
        user.setRoles(roles);
        userServiceImp.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImp.delete(id);
        return "redirect:/admin";
    }
}
