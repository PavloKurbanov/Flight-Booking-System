package ui.showCommand;

import entity.Ticket;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import util.TicketPrinter;

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
        List<Ticket> tickets = ticketService.getAll();

        if (tickets.isEmpty()) {
            System.out.println("Немає проданих квитків.");
            return;
        }
        TicketPrinter.printTicket(tickets, passengerService, flightService);
    }
}
