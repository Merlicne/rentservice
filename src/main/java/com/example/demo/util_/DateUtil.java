package com.example.demo.util_;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate toLocalDate(String date_string) {
        return LocalDate.parse(date_string, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static String toString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
