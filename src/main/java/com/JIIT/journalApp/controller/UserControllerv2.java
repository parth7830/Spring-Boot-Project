package com.JIIT.journalApp.controller;

import com.JIIT.journalApp.api.response.WeatherResponse;
import com.JIIT.journalApp.entity.JournalEntry;
import com.JIIT.journalApp.entity.User;
import com.JIIT.journalApp.services.JournalEntryService;
import com.JIIT.journalApp.services.UserService;
import com.JIIT.journalApp.services.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserControllerv2 {

    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;
//    @GetMapping
//    public List<User> getAllUsers(){
//        return userService.getAll();
//    }
    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weatherResponse != null){
            greeting = "Weather feels Like" + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi" + authentication.getName() + greeting ,HttpStatus.OK);
    }
}