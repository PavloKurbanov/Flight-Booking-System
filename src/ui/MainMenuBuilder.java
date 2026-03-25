package ui;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.buyTicketCommand.BuyTicketCommand;
import ui.command.Command;
import ui.ticket.RemoveTicketCommand;
import ui.showCommand.*;

import java.util.HashMap;
import java.util.Map;

public record MainMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService) {

    public Map<String, Command> buildCommands() {
        Map<String, Command> map = new HashMap<>();

        Command showMenuCommand = new ShowMenuCommand(inputOutput, flightService, passengerService, ticketService);
        Command buyTicket = new BuyTicketCommand(inputOutput, passengerService, flightService, ticketService);
        Command removeTicket = new RemoveTicketCommand(inputOutput, ticketService, passengerService, flightService);
        Command showAllPassengerTickets = new ShowAllPassengerTickets(inputOutput, ticketService, flightService, passengerService);
        Command showAllFlightTickets = new ShowAllFlightTickets(inputOutput, ticketService, flightService, passengerService);

        map.put(showMenuCommand.choice(), showMenuCommand);
        map.put(buyTicket.choice(), buyTicket);
        map.put(removeTicket.choice(), removeTicket);
        map.put(showAllPassengerTickets.choice(), showAllPassengerTickets);
        map.put(showAllFlightTickets.choice(), showAllFlightTickets);

        return map;
    }
}
