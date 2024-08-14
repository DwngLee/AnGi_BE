package com.personal.project.angi.model.dto.request;

import co.elastic.clients.elasticsearch._types.SortOrder;
import lombok.Data;

@Data
public class SortRequest {
    private String sortField;
    private SortOrder sortDirection;
}
