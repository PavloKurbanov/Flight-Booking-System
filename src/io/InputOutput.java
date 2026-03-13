package io;

import util.DateFormatter;

import java.time.LocalDate;
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

    public LocalDate readDate(String prompt) {
        System.out.println(prompt);
        LocalDate localDate = null;

        while (localDate == null) {
            try {
                localDate = DateFormatter.parse(scanner.nextLine());
            } catch (IllegalArgumentException | DateTimeParseException e) {
                System.err.println("ПОМИЛКА: Невірний формат дати. " + e.getMessage());
            }
        }
        return localDate;
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
}
