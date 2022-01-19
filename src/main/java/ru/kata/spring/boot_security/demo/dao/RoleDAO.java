package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Set;


public interface RoleDAO {
    List<Role> findAll();
    Role getById(int id);

    public Set<Role> setRolesByIds(String rolesId);
}
