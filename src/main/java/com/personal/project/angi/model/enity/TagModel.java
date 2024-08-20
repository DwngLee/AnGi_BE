package com.personal.project.angi.model.enity;

import com.personal.project.angi.model.basemodel.TagBaseModel;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tag")
public class TagModel extends TagBaseModel {
}
