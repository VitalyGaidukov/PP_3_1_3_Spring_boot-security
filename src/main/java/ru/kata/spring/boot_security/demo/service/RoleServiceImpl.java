package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;


import java.util.List;
import java.util.Set;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleDAO roleDAO;

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleDAO roleRepository, RoleRepository roleRepository1) {
        this.roleDAO = roleRepository;
        this.roleRepository = roleRepository1;
    }


    @Override
    public Role getById(int id) {
        return roleDAO.getById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> listRoles() {
        return roleRepository.findAllBy();
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> setRolesByIds(String rolesId) {
        return roleDAO.setRolesByIds(rolesId);
    }
}
