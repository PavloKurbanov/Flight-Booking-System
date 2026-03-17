package ui;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import ui.showCommand.ShowAllFlight;
import ui.showCommand.ShowAllPassenger;
import ui.showCommand.ShowAllTicket;

import java.util.HashMap;
import java.util.Map;

public record MainMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService) {

    public Map<String, Command> showMenu() {
        Map<String, Command> map = new HashMap<>();

        Command showAllFlight = new ShowAllFlight(flightService);
        Command showAllPassenger = new ShowAllPassenger(passengerService);
        Command showAllTicket = new ShowAllTicket(ticketService, flightService, passengerService);

        map.put(showAllFlight.choice(), showAllFlight);
        map.put(showAllPassenger.choice(), showAllPassenger);
        map.put(showAllTicket.choice(), showAllTicket);

        return map;
    }
}
