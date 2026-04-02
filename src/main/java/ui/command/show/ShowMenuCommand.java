package ui.command.show;

import domain.ticket.TicketMapper;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.Command;
import ui.menu.ShowMenu;

@MenuItem(action = 2, description = "Показати інформацію")
public class ShowMenuCommand implements Command {
    private final ShowMenu showMenu;

    public ShowMenuCommand(InputOutput inputOutput, FlightService flightService, PassengerService passengerService, TicketService ticketService, TicketMapper ticketMapper) {
        this.showMenu = new ShowMenu(inputOutput, flightService, passengerService, ticketService, ticketMapper);
    }

    @Override
    public void command() {
        showMenu.showMenu();
    }
}
