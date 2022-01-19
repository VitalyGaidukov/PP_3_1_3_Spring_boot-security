package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final RoleRepository roleRepository;
    private final UserDAO userDAO;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleRepository roleRepository, UserDAO userDAO, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void saveUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userDAO.saveUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public void updateUser(User user, String role) {
        user.setRoles(roleService.setRolesByIds(role));
        userDAO.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }


    @Override
    public boolean isUniqueUsername(String username) {
        return findByUsername(username) != null;
    }
}
