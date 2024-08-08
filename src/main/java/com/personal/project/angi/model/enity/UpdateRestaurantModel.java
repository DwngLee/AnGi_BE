package com.personal.project.angi.model.enity;

import com.personal.project.angi.model.basemodel.UpdateRestaurantBaseModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "updateRestaurant")
public class UpdateRestaurantModel extends UpdateRestaurantBaseModel {
}
