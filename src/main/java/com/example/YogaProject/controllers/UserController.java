package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.User;
import com.example.YogaProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public String createUserForm(){
        return "create-user";
    }

    @PostMapping("/create-user")
    public String createUser(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String birth
    ) {
        LocalDate date;
        User user;
        if (!birth.isEmpty() && birth.matches("^\\d+-\\d+-\\d+")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date= LocalDate.parse(birth, formatter);
            user = new User(firstName,lastName,email,phoneNumber,date);
        } else {
            user = new User(firstName, lastName, email, phoneNumber, null);
        }
        userService.saveUser(user);
        return "redirect:/users";
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
        return "update-user";
    }

    @PostMapping("/update-user")
    public String updateUser(User user){
        userService.saveUser(user);
        return "redirect:/users";
    }

    @PostMapping("/users/filter")
    public String filter(@RequestParam String lastName, Model model) {
        List<User> users;
        if(lastName != null && !lastName.isEmpty()) {
            users = userService.findByLastName(lastName);
        } else {
            users = userService.findAll();
        }
        model.addAttribute("users", users);
        return "users-list";
    }

}
