package ui;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;

import java.util.HashMap;
import java.util.Map;

public record MainMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService) {

    public Map<String, Command> showMenu(){
        Map<String, Command> map = new HashMap<>();

        Command showAllFlight = new ShowAllFlight(flightService);

        map.put(showAllFlight.choice(), showAllFlight);

        return map;
    }
}
