package com.personal.project.angi.model.enity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personal.project.angi.model.basemodel.RestaurantElkBaseModel;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "restaurant_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantElkModel extends RestaurantElkBaseModel {

}
