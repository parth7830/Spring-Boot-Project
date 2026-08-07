package com.JIIT.journalApp.scheduler;

import com.JIIT.journalApp.entity.JournalEntry;
import com.JIIT.journalApp.entity.User;
import com.JIIT.journalApp.repository.UserRepositoryImpl;
import com.JIIT.journalApp.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * SUN") // Every Sunday at 9:00 AM
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(JournalEntry::getContent)
                    .collect(Collectors.toList());

            String entry = String.join(" ", filteredEntries);

            if (!entry.isEmpty()) {
                String subject = "Weekly Sentiment Analysis - Journal Summary";
                String body = "Hi " + user.getUserName() + ",\n\n"
                        + "Here is your weekly journal summary:\n\n"
                        + entry + "\n\n"
                        + "Regards,\nJournal App";
                try {
                    emailService.sendSimpleEmail(user.getEmail(), subject, body);
                    log.info("Sentiment analysis email sent to: {}", user.getEmail());
                } catch (Exception e) {
                    log.error("Failed to send SA email to {}: {}", user.getEmail(), e.getMessage());
                }
            }
        }
    }
}
