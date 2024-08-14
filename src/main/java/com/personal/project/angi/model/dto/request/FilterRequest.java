package com.personal.project.angi.model.dto.request;

import lombok.Data;

@Data
public class FilterRequest {
    private String filterField;
    private Object fliterValue;
    private String filterOperations;
}
