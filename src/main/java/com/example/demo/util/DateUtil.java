package com.example.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }


    public static LocalDate toLocalDate(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }

    public static String toString(LocalDate date) {
        return date.format(formatter);
    }
}
