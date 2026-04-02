package menu;

import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import framework.menuEngine.MenuEngine;
import framework.menuEngine.menuValidation.MenuGroup;
import infrastructure.io.InputOutput;
import ui.command.Command;
import ui.command.RegistrationFlightCommand;
import ui.command.RegistrationPassengerCommand;

import java.util.List;
import java.util.Map;

public record RegistrationMenuBuilder(InputOutput inputOutput, FlightService flightService, TicketService ticketService,
                                      PassengerService passengerService) {

    public Map<Integer, Command> showMenu() {

        Command registrationPassenger = new RegistrationPassengerCommand(inputOutput, passengerService);
        Command registrationFlight = new RegistrationFlightCommand(inputOutput, flightService);

        List<Command> commands = List.of(registrationPassenger,
                registrationFlight
        );

        return MenuEngine.commands(commands, MenuGroup.REGISTRATION);
    }
}
