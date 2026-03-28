package ui.menu;

import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketMapper;
import domain.ticket.TicketService;
import framework.menuEngine.MenuEngine;
import framework.menuEngine.menuValidation.MenuGroup;
import infrastructure.io.InputOutput;
import ui.command.BuyTicketCommand;
import ui.command.Command;
import ui.command.RegistrationMenuCommand;
import ui.command.RemoveTicketCommand;
import ui.command.show.ShowMenuCommand;

import java.util.List;
import java.util.Map;

public record MainMenuBuilder(InputOutput inputOutput, FlightService flightService, PassengerService passengerService,
                              TicketService ticketService, TicketMapper ticketMapper) {

    public Map<Integer, Command> buildCommands() {
        Command registrationMenuCommand = new RegistrationMenuCommand(inputOutput, flightService, ticketService, passengerService);
        Command showMenuCommand = new ShowMenuCommand(inputOutput, flightService, passengerService, ticketService, ticketMapper);
        Command buyTicket = new BuyTicketCommand(inputOutput, passengerService, flightService, ticketService);
        Command removeTicket = new RemoveTicketCommand(inputOutput, ticketService, passengerService, ticketMapper);

        List<Command> mainMenuList = List.of(
                registrationMenuCommand,
                showMenuCommand,
                buyTicket,
                removeTicket
        );

        return MenuEngine.commands(mainMenuList, MenuGroup.MAIN);
    }
}
