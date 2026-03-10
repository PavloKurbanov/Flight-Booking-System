package util;

import entity.Flight;

import java.util.List;

public class FlightPrinter {
    public static void printFlights(List<Flight> flights) {
        System.out.printf(
                "%-5s %-10s %-10s %-20s %-10s%n",
                "ID", "FROM", "TO", "DEPARTURE", "SEATS"
        );

        System.out.println("--------------------------------------------------------");

        for (Flight f : flights) {
            System.out.printf(
                    "%-5d %-10s %-10s %-20s %d/%d%n",
                    f.getId(),
                    f.getDepartureCity(),
                    f.getArrivalCity(),
                    DateFormatter.format(f.getDepartureTime()),
                    f.getAvailableSeats(),
                    f.getTotalSeats()
            );
        }
    }
}
