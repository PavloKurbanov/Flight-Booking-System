package ui.command.show;

import domain.ticket.Ticket;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketDTO;
import domain.ticket.TicketMapper;
import domain.ticket.TicketService;
import ui.command.Command;
import infrastructure.util.TicketPrinter;

import java.lang.annotation.Target;
import java.util.List;

public class ShowAllTicket implements Command {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public ShowAllTicket(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
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
        List<TicketDTO> dtoList = ticketMapper.toDTOList(tickets);
        TicketPrinter.printTicket(dtoList);
    }
}
