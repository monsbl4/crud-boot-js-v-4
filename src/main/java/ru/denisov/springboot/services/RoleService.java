package ru.denisov.springboot.services;


import ru.denisov.springboot.models.Role;

import java.util.List;

public interface RoleService  {
    List<Role> getRoles ();
    public Role getRoleByName(String roleName);
}
