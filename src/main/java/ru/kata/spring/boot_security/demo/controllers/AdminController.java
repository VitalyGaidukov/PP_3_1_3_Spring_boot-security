package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "admin/admin_all_users";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model){
        User user = new User();
        List<Role> roles = roleService.listRoles();
        model.addAttribute("allRoles", roles);
        model.addAttribute("user", user);
        return "admin/user_info";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser (@PathVariable("id") int id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/get")
    public String getUser(Model model, @PathVariable("id") int id){
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roleList", roleService.listRoles());
        return "admin/get";
    }

    @PatchMapping("/{id}")
    public String updateUser (@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(user,id);
        return "redirect:/admin";
    }
}
