package com.personal.project.angi.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagResponse {
    private String id;

    private String tagName;

    private String tagDescription;
}
