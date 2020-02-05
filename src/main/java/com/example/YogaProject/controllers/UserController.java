package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.User;
import com.example.YogaProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
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
        return "create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@Valid User user,
                             @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                             BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "create-user";
        }
        user.setBirth(date);
        /*LocalDate date;
        if (!birth.isEmpty() && birth.matches("^\\d+-\\d+-\\d+")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date= LocalDate.parse(birth, formatter);
            user = new User(firstName,lastName,email,phoneNumber,date);
        } else {
            user = new User(firstName, lastName, email, phoneNumber, null);
        }*/
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
    public String updateUser(User user,
                             @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        user.setBirth(date);
        userService.saveUser(user);
        return "redirect:/users";
    }
}
