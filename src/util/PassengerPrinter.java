package util;

import entity.Passenger;

import java.util.List;

public class PassengerPrinter {
    public static void printPassenger(List<Passenger> passengers) {
        System.out.printf(
                "%-5s %-10s %-10s",
                "ID", "FIRST NAME", "LAST NAME"
        );

        System.out.println("--------------------------------------------------------");

        for (Passenger p : passengers) {
            System.out.printf(
                    "%-5d %-10s %-10s",
                    p.getId(),
                    p.getFirstName(),
                    p.getLastName()
            );
        }
    }
}
