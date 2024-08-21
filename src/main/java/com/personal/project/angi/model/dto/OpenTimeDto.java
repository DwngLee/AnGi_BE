package com.personal.project.angi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenTimeDto {
    private Boolean isDayOff;

    private String dayOfWeek;

    private String openingTime;

    private String closingTime;

}
