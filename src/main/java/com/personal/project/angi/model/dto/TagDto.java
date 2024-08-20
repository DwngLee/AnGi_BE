package com.personal.project.angi.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto {
    private String tagName;

    private String tagDescription;
}
