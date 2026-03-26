package infrastructure.util;

import domain.flight.Flight;
import domain.passenger.Passenger;
import domain.ticket.Ticket;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketDTO;

import java.util.List;

public class TicketPrinter {
    public static void printTicket(List<TicketDTO> tickets) {
        System.out.println("--- КВИТКИ --- ");
        System.out.printf("%-5s %-15s %-15s %-20s %-5s%n", "ID", "FROM", "TO", "PASSENGER", "PRICE");
        System.out.println("--------------------------------------------------------");

        for (TicketDTO t : tickets) {
            System.out.printf("%-5d %-15s %-15s %-20s %d$%n",
                    t.id(), t.departureCity(), t.arrivalCity(), t.passengerFullName(), t.price());
        }
    }
}
