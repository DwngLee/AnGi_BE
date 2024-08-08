package com.personal.project.angi.model.enity;

import com.personal.project.angi.model.basemodel.UserElkBaseModel;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user_info")
public class UserElkModel extends UserElkBaseModel {
}
