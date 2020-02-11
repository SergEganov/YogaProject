package com.example.YogaProject.controllers;

import com.example.YogaProject.domain.Role;
import com.example.YogaProject.domain.User;
import com.example.YogaProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/users/users";
    }

    @GetMapping("/create-user")
    public String createUserForm(Model model,
                                 @AuthenticationPrincipal User user){
        if (user != null && user.getRoles().contains(Role.ADMIN)) {
            model.addAttribute("admin", true);
        }
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "/users/create-user";
    }

    @PostMapping("/create-user")
    public String createUser(@Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors() || !userService.createUserValidation(user, bindingResult)) {
            model.addAttribute("roles", Role.values());
            return "/users/create-user";
        }
        if (user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(Role.USER));
        }
        userService.saveUser(user);
        return "redirect:/main";
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
        return "/users/update-user";
    }

    @PostMapping("/update-user")
    public String updateUser(@Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors() || !userService.updateUserValidation(user, bindingResult)) {
            model.addAttribute("roles", Role.values());
            return "/users/update-user";
        } else {
            userService.saveUser(user);
            return "redirect:/users";
        }
    }
}
