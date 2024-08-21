package com.personal.project.angi.repository.elk;

import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.model.enity.UserElkModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantElkRepository extends ElasticsearchRepository<RestaurantElkModel, String> {
}
