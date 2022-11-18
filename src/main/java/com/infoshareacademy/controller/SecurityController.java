package com.infoshareacademy.controller;

import com.infoshareacademy.entity.user.User;
import com.infoshareacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    private final UserService userService;

    @Autowired
    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String signup(Model model){
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/register")
    public String register(User user){
        userService.addUser(user);
        return "/login";
    }

}
