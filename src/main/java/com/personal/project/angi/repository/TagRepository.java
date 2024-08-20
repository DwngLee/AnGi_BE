package com.personal.project.angi.repository;

import com.personal.project.angi.model.enity.TagModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<TagModel, String> {
}
