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
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private Set<Role>roles = new HashSet<>();
    private Set<Role>rolesUser = new HashSet<>();
    @PostConstruct
    private void postConstruct(){
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleDao.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleDao.save(roleUser);
        roles.add(roleAdmin);
        roles.add(roleUser);
        rolesUser.add(roleUser);
        User userAdmin = new User("serg","den","admin@admin.ru",32,"admin","admin");
        userAdmin.setRoles(roles);
        userService.save(userAdmin);
        User userUser = new User("Oleg", "Petrov", "user@user.ru",44,"user","user");
        userUser.setRoles(rolesUser);
        userService.save(userUser);
    }
}
