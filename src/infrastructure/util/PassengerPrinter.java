package infrastructure.util;

import domain.passenger.Passenger;

import java.util.List;

public class PassengerPrinter {
    public static void printPassenger(List<Passenger> passengers) {

        System.out.println("--- ПАСАЖИРИ ---");
        System.out.printf(
                "%-5s %-10s %-10s%n",
                "ID", "FIRST NAME", "LAST NAME"
        );

        System.out.println("--------------------------------------------------------");

        for (Passenger p : passengers) {
            System.out.printf(
                    "%-5d %-10s %-10s%n",
                    p.getId(),
                    p.getFirstName(),
                    p.getLastName()
            );
        }
    }
}
