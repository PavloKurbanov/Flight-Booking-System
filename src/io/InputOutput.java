package io;

import util.DateFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputOutput {
    private final Scanner scanner;

    public InputOutput() {
        this.scanner = new Scanner(System.in);
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public Integer readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть число!");
            }
        }
    }

    public Long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть число!");
            }
        }
    }

    public LocalDateTime readDateTime(String prompt) {
        System.out.print(prompt);
        LocalDateTime dateTime = null;
        while (dateTime == null) {
            try {
                dateTime = DateFormatter.parseLocalDateTime(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("ПОМИЛКА: Невірний формат дати. " + e.getMessage());
            }
        }
        return dateTime;
    }
}
