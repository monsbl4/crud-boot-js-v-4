package ru.denisov.springboot.dao;


import ru.denisov.springboot.models.Role;
import ru.denisov.springboot.models.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    public List<User> index();
    public User show (int id);
    public void save(User user);
    public void update(User updatedUser);
    public void delete(int id);
    public User getUserByUsername(String username);
    public void saveWithRole(User user, Set<Role> roleSet);
    public User getUserByEmail(String email);
}
