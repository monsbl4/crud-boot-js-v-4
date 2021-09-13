package ru.denisov.springboot.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.denisov.springboot.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> index();
    public User show (int id);
    public User save(User user);
    public User update(User updatedUser);
    public void delete(int id);
    public User getUserByUsername(String username);
    public User getUserById(int id);
}
