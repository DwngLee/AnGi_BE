package com.personal.project.angi.model.enity;

import com.personal.project.angi.model.basemodel.UserInfoBaseModel;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "userInfo")
public class UserInfoModel extends UserInfoBaseModel {
}
