package com.JIIT.journalApp.controller;

import com.JIIT.journalApp.entity.JournalEntry;
import com.JIIT.journalApp.entity.User;
import com.JIIT.journalApp.services.JournalEntryService;
import com.JIIT.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {

        User user = userService.findByUserName(userName);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<JournalEntry> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>("No journal entries found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName) {
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myID}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myID){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myID);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("id/{userName}/{myID}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myID, @PathVariable String userName){
        journalEntryService.deleteById(myID,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//    @PutMapping
//    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry){
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if(old != null){
//            old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
//            old.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
//            journalEntryService.saveEntry(old, user);
//            return new ResponseEntity<>(old,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
}