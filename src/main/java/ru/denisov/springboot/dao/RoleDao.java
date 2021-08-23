package ru.denisov.springboot.dao;


import ru.denisov.springboot.models.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRoles();
    public void save (Role role);

}
