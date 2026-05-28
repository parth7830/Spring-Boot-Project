package com.JIIT.journalApp.controller;

import com.JIIT.journalApp.entity.JournalEntry;
import com.JIIT.journalApp.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerv2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }
    @GetMapping("id/{myID}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myID){
        return journalEntryService.findById(myID).orElse(null) ;
    }
    @DeleteMapping("id/{myID}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myID){
        journalEntryService.deleteById(myID);
        return true;
    }
    @PutMapping
    public JournalEntry updateJournalById(@PathVariable ObjectId id,@RequestBody JournalEntry myEntry){
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old != null){
            old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : old.getTitle());
            old.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
       return old  ;
    }
}