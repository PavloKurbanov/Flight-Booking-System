package ui.menu;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;

import java.util.Map;

public class RegistrationMenu {
    private final InputOutput inputOutput;
    private final Map<String, Command> commands;

    public RegistrationMenu(InputOutput inputOutput, FlightService flightService, TicketService ticketService, PassengerService passengerService) {

    }
}
