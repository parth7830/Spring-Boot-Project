package com.JIIT.journalApp.repository;

import com.JIIT.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// controller --> service --> repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
