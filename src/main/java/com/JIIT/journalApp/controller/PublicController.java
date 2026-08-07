package com.JIIT.journalApp.controller;

import com.JIIT.journalApp.entity.User;
import com.JIIT.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthcheck(){
        return "OK";
    }
    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }
}
