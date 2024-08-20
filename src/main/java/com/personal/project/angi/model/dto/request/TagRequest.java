package com.personal.project.angi.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagRequest {
    private String tagName;

    private String tagDescription;
}
