package ui.showCommand;

import entity.Flight;
import entity.Passenger;
import entity.Ticket;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;

import java.util.List;

public class ShowAllTicket implements Command {
    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    public ShowAllTicket(TicketService ticketService, FlightService flightService, PassengerService passengerService) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    @Override
    public String choice() {
        return "3";
    }

    @Override
    public void command() {
        List<Ticket> ticketServiceAll = ticketService.getAll();

        if (ticketServiceAll.isEmpty()) {
            System.out.println("На жаль, не має жодного квитка.");
            return;
        }

        System.out.printf(
                "%-5s %-10s %-10s %-20s %-5s%n",
                "ID", "FROM", "TO", "PASSENGER", "PRICE"
        );

        System.out.println("--------------------------------------------------------");

        for (Ticket ticket : ticketServiceAll) {
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
