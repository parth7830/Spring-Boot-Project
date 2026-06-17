package com.JIIT.journalApp.services;

import com.JIIT.journalApp.entity.JournalEntry;
import com.JIIT.journalApp.entity.User;
import com.JIIT.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {
        try {

            User user = userService.findByUserName(userName);

            if (user == null) {
                throw new RuntimeException("User not found: " + userName);
            }

            journalEntry.setDate(LocalDateTime.now());

            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);

            user.getJournalEntries().add(savedEntry);

            userService.saveEntry(user);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "An error occurred while saving the journal entry",
                    e
            );
        }
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
