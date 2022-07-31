package com.infoshareacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping(path ="/api/user", produces = "application/json")
    public String user() {
        return  "Hello world!";
    }

}
