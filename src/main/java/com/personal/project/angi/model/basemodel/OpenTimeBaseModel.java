package com.personal.project.angi.model.basemodel;

import com.personal.project.angi.enums.WeekDayEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
public class OpenTimeBaseModel {
    @Id
    private String id;

    private Boolean isDayOff;

    private WeekDayEnum dayOfWeek;

    private String openingTime;

    private String closingTime;

    @Field("createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field("updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
