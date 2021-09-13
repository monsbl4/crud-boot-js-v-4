package ru.denisov.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.denisov.springboot.dao.RoleDao;
import ru.denisov.springboot.dao.UserDao;
import ru.denisov.springboot.models.Role;
import ru.denisov.springboot.models.User;
import ru.denisov.springboot.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleDao roleDao;

    private Set<Role> roles = new HashSet<>();
    private Set<Role> rolesAdmin = new HashSet<>();
    private Set<Role> rolesUser = new HashSet<>();

    @PostConstruct
    private void postConstruct() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleDao.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleDao.save(roleUser);
        roles.add(roleAdmin);
        roles.add(roleUser);
        rolesAdmin.add(roleAdmin);
        rolesUser.add(roleUser);
        User userAdmin = new User("serg", "den", "admin@admin.ru", 32, "admin", "admin");
        userAdmin.setRoles(roles);
        User admin2 = new User("admin", "adminL", "admin2@admin.ru", 44, "admin2", "admin");
        admin2.setRoles(rolesAdmin);
        userService.save(userAdmin);
        User userUser = new User("Oleg", "Petrov", "user@user.ru", 44, "user", "user");
        User user2 = new User("User2", "user2", "user2@user.ru", 13, "user2", "user2");
        user2.setRoles(rolesUser);
        userUser.setRoles(rolesUser);
        userService.save(userUser);
        userService.save(user2);
        userService.save(admin2);
    }
}
