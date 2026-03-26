package infrastructure.util;

import domain.ticket.TicketDTO;

import java.util.List;

public class TicketPrinter {
    public static void printTicket(List<TicketDTO> tickets) {
        System.out.println("--- КВИТКИ --- ");
        System.out.printf("%-5s %-15s %-15s %-20s%n", "ID", "FROM", "TO", "PASSENGER");
        System.out.println("--------------------------------------------------------");

        for (TicketDTO t : tickets) {
            System.out.printf("%-5d %-15s %-15s %-20s$%n",
                    t.id(), t.departureCity(), t.arrivalCity(), t.passengerFullName());
        }
    }
}
