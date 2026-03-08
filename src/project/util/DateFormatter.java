package project.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static String format(LocalDateTime date) {
        return (date == null) ? "N/A" : date.format(FORMATTER);
    }

    public static LocalDateTime parse(String text) throws DateTimeParseException {
        return LocalDateTime.parse(text, FORMATTER);
    }
}
