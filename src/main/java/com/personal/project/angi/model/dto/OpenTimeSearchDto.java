package com.personal.project.angi.model.dto;

import com.personal.project.angi.enums.WeekDayEnum;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class OpenTimeSearchDto {
    @Field(type = FieldType.Boolean)
    private Boolean isDayOff;

    @Field(type = FieldType.Keyword)
    private WeekDayEnum dayOfWeek;

    @Field(type = FieldType.Text)
    private String openingTime;

    @Field(type = FieldType.Text)
    private String closingTime;
}
