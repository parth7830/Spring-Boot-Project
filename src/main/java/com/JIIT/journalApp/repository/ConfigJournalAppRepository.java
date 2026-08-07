package com.JIIT.journalApp.repository;

import com.JIIT.journalApp.entity.ConfigJournalAppEntity;
import com.JIIT.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// controller --> service --> repository
public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
