package com.personal.project.angi.repository;

import com.personal.project.angi.model.enity.RestaurantModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends MongoRepository<RestaurantModel, String> {
}
