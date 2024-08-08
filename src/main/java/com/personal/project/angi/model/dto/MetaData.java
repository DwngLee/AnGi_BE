package com.personal.project.angi.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetaData {
    private int totalPage;
    private int currentPage;
    private int pageSize;
}
