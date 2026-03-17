package util;

import entity.Flight;
import entity.Passenger;
import entity.Ticket;
import service.FlightService;
import service.PassengerService;

import java.util.List;

public class TicketPrinter {
    public static void printTicket(List<Ticket> tickets, PassengerService passengerService, FlightService flightService) {
        System.out.println("--- КВИТКИ --- ");

        System.out.printf(
                "%-5s %-15s %-15s %-20s %-5s%n",
                "ID", "FROM", "TO", "PASSENGER", "PRICE"
        );

        System.out.println("--------------------------------------------------------");

        for (Ticket ticket : tickets) {
            Long flightId = ticket.getFlightId();
            Long passengerId = ticket.getPassengerId();

            Passenger passenger = passengerService.findById(passengerId);
            Flight flight = flightService.findById(flightId);

            String fullName = passenger.getFirstName() + " " + passenger.getLastName();

            System.out.printf(
                    "%-5d %-15s %-15s %-20s %d$%n",
                    ticket.getId(),
                    flight.getDepartureCity(),
                    flight.getArrivalCity(),
                    fullName,
                    ticket.getPrice()
            );
        }
    }
}
