package ui.menu;

import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.RegistrationMenuCommand;
import ui.command.show.ShowMenuCommand;
import ui.command.BuyTicketCommand;
import ui.command.Command;
import ui.command.RemoveTicketCommand;

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
