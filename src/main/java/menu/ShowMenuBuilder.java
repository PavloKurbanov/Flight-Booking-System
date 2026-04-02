package menu;

import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketMapper;
import domain.ticket.TicketService;
import framework.menuEngine.MenuEngine;
import framework.menuEngine.menuValidation.MenuGroup;
import infrastructure.io.InputOutput;
import ui.command.Command;
import ui.command.show.*;

import java.util.List;
import java.util.Map;

public record ShowMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService, TicketMapper ticketMapper) {

    public Map<Integer, Command> showMenu() {

        Command showAllFlight = new ShowAllFlight(flightService);
        Command showAllPassenger = new ShowAllPassenger(passengerService);
        Command showAllTicket = new ShowAllTicket(ticketService, ticketMapper);
        Command showAllPassengerTickets = new ShowAllPassengerTickets(inputOutput, ticketService, ticketMapper);
        Command showAllFlightTickets = new ShowAllFlightTickets(inputOutput, ticketService, flightService, ticketMapper);


        List<Command> ticket = List.of(showAllTicket,
                showAllFlightTickets,
                showAllFlight,
                showAllPassenger,
                showAllPassengerTickets);

        return MenuEngine.commands(ticket, MenuGroup.SHOW);
    }
}
