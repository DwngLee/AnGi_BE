package com.personal.project.angi.repository;

import com.personal.project.angi.model.enity.UserInfoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserInfoModel, String> {
    Optional<UserInfoModel> findByUsername(String username);
}
