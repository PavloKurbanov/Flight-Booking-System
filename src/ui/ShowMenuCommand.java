package ui;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import ui.menu.ShowMenu;

public class ShowMenuCommand implements Command {
    private final ShowMenu showMenu;

    public ShowMenuCommand(InputOutput inputOutput, FlightService flightService, PassengerService passengerService, TicketService ticketService) {
        this.showMenu = new ShowMenu(inputOutput, flightService, passengerService, ticketService);
    }

    @Override
    public String choice() {
        return "2";
    }

    @Override
    public void command() {
        showMenu.showMenu();
    }
}
