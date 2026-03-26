package ui.registrationCommand;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import ui.menu.RegistrationMenu;

public class RegistrationMenuCommand implements Command {
    private final RegistrationMenu registrationMenu;

    public RegistrationMenuCommand(InputOutput inputOutput, FlightService flightService, TicketService ticketService, PassengerService passengerService) {
        this.registrationMenu = new RegistrationMenu(inputOutput, flightService, ticketService, passengerService);
    }

    @Override
    public String choice() {
        return "1";
    }

    @Override
    public void command() {
        registrationMenu.showMenu();
    }
}
