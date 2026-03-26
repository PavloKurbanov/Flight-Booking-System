package ui.menu;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import ui.showCommand.*;

import java.util.HashMap;
import java.util.Map;

public record ShowMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService) {

    public Map<String, Command> showMenu() {
        Map<String, Command> map = new HashMap<>();

        Command showAllFlight = new ShowAllFlight(flightService);
        Command showAllPassenger = new ShowAllPassenger(passengerService);
        Command showAllTicket = new ShowAllTicket(ticketService, flightService, passengerService);
        Command showAllPassengerTickets = new ShowAllPassengerTickets(inputOutput, ticketService, flightService, passengerService);
        Command showAllFlightTickets = new ShowAllFlightTickets(inputOutput, ticketService, flightService, passengerService);

        map.put(showAllFlight.choice(), showAllFlight);
        map.put(showAllPassenger.choice(), showAllPassenger);
        map.put(showAllTicket.choice(), showAllTicket);
        map.put(showAllPassengerTickets.choice(), showAllPassengerTickets);
        map.put(showAllFlightTickets.choice(), showAllFlightTickets);

        return map;
    }
}
