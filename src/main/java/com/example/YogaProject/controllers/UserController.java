package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users-list";
    }

    @GetMapping("/create-user")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors() || !userService.createUserValidation(user, bindingResult)) {
            model.addAttribute("roles", Role.values());
            return "create-user";
        }
        userService.saveUser(user);
        return "redirect:/users";
       /*if (userService.createUserValidation(user, bindingResult)) {
           userService.saveUser(user);
           return "redirect:/users";
       } else {
           model.addAttribute("roles", Role.values());
           return "create-user";
       }*/
    }

    @GetMapping("delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/update-user/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "update-user";
    }

    @PostMapping("/update-user")
    public String updateUser(@Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        if (userService.updateUserValidation(user, bindingResult)) {
            userService.saveUser(user);
            return "redirect:/users";
        } else {
            model.addAttribute("roles", Role.values());
            return "update-user";
        }
    }
}
