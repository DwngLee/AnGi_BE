package com.personal.project.angi.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {
    public final String RESPONSE_DATE_FORMAT = "yyyy-MM-dd";
    public final String RESPONSE_TIME_FORMAT = "HH:mm:ss";

    public LocalDate convertDateResponseToLocalDate(String dateStr, String pattern) {
        if (!Util.isNullOrEmpty(dateStr)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return LocalDate.parse(dateStr, formatter);
        }

        return null;
    }

    public LocalTime convertDateResponseToLocalTime(String timeStr, String pattern) {
        if (!Util.isNullOrEmpty(timeStr)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return LocalTime.parse(timeStr, formatter);
        }

        return null;
    }
}
