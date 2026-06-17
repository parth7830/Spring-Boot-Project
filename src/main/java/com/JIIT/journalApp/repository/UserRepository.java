package com.JIIT.journalApp.repository;

import com.JIIT.journalApp.entity.JournalEntry;
import com.JIIT.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// controller --> service --> repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);
}
