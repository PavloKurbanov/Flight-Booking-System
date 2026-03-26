package ui.menu;

import domain.ticket.TicketMapper;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.Command;
import ui.command.show.*;

import java.util.HashMap;
import java.util.Map;

public record ShowMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService, TicketMapper ticketMapper) {

    public Map<String, Command> showMenu() {
        Map<String, Command> map = new HashMap<>();

        Command showAllFlight = new ShowAllFlight(flightService);
        Command showAllPassenger = new ShowAllPassenger(passengerService);
        Command showAllTicket = new ShowAllTicket(ticketService, ticketMapper);
        Command showAllPassengerTickets = new ShowAllPassengerTickets(inputOutput, ticketService, ticketMapper);
        Command showAllFlightTickets = new ShowAllFlightTickets(inputOutput, ticketService, flightService, ticketMapper);

        map.put(showAllFlight.choice(), showAllFlight);
        map.put(showAllPassenger.choice(), showAllPassenger);
        map.put(showAllTicket.choice(), showAllTicket);
        map.put(showAllPassengerTickets.choice(), showAllPassengerTickets);
        map.put(showAllFlightTickets.choice(), showAllFlightTickets);

        return map;
    }
}
