package com.personal.project.angi.model.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
public class TagResponse {
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String tagName;

    @Field(type = FieldType.Text)
    private String tagDescription;
}
