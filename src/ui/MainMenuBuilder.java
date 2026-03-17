package ui;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.buyTicketCommand.BuyTicketCommand;
import ui.command.Command;
import ui.removeTicketCommand.RemoveTicketCommand;
import ui.showCommand.ShowAllFlight;
import ui.showCommand.ShowAllPassenger;
import ui.showCommand.ShowAllTicket;

import java.util.HashMap;
import java.util.Map;

public record MainMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService) {

    public Map<String, Command> buildCommands() {
        Map<String, Command> map = new HashMap<>();

        Command showAllFlight = new ShowAllFlight(flightService);
        Command showAllPassenger = new ShowAllPassenger(passengerService);
        Command showAllTicket = new ShowAllTicket(ticketService, flightService, passengerService);
        Command buyTicket = new BuyTicketCommand(inputOutput, passengerService, flightService, ticketService);
        Command removeTicket = new RemoveTicketCommand(inputOutput, ticketService, passengerService, flightService);

        map.put(showAllFlight.choice(), showAllFlight);
        map.put(showAllPassenger.choice(), showAllPassenger);
        map.put(showAllTicket.choice(), showAllTicket);
        map.put(buyTicket.choice(), buyTicket);
        map.put(removeTicket.choice(), removeTicket);

        return map;
    }
}
