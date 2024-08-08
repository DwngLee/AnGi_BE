package com.personal.project.angi.model.enity;

import com.personal.project.angi.model.basemodel.RestaurantBaseModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "restaurant")
public class RestaurantModel extends RestaurantBaseModel {
}
