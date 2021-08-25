package ru.denisov.springboot.dao;


import ru.denisov.springboot.models.Role;

import java.util.List;
import java.util.Set;


public interface RoleDao {
    List<Role> getRoles();
    public void save (Role role);
    public Role getRoleByName(String roleName);

}
