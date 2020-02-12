package com.example.YogaProject.controllers;

import com.example.YogaProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String greeting(Model model) {
        return "first";
    }

    @GetMapping("/main")
    public String main(Model model) {
        return "main";
    }
}
