package ui.menu;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.registrationCommand.RegistrationMenuCommand;
import ui.showCommand.ShowMenuCommand;
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

        Command registrationMenuCommand = new RegistrationMenuCommand(inputOutput, flightService, ticketService, passengerService);
        Command showMenuCommand = new ShowMenuCommand(inputOutput, flightService, passengerService, ticketService);
        Command buyTicket = new BuyTicketCommand(inputOutput, passengerService, flightService, ticketService);
        Command removeTicket = new RemoveTicketCommand(inputOutput, ticketService, passengerService, flightService);


        map.put(registrationMenuCommand.choice(), registrationMenuCommand);
        map.put(showMenuCommand.choice(), showMenuCommand);
        map.put(buyTicket.choice(), buyTicket);
        map.put(removeTicket.choice(), removeTicket);

        return map;
    }
}
