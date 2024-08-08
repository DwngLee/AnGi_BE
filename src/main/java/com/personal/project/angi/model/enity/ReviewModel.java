package com.personal.project.angi.model.enity;

import com.personal.project.angi.model.basemodel.ReviewBaseModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "review")
public class ReviewModel extends ReviewBaseModel {
}
