package com.personal.project.angi.model.enity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.personal.project.angi.model.basemodel.UserElkBaseModel;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserElkModel extends UserElkBaseModel {
}
