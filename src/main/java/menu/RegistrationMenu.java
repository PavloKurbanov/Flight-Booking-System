package menu;

import framework.menuPrinter.MenuPrinter;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.Command;

import java.util.Map;

public class RegistrationMenu {
    private final InputOutput inputOutput;
    private final Map<Integer, Command> commands;

    public RegistrationMenu(InputOutput inputOutput, FlightService flightService, TicketService ticketService, PassengerService passengerService) {
        this.inputOutput = inputOutput;
        RegistrationMenuBuilder registrationMenuBuilder = new RegistrationMenuBuilder(inputOutput, flightService, ticketService, passengerService);
        this.commands = registrationMenuBuilder.showMenu();
    }

    public void showMenu() {
        while (true) {
            MenuPrinter.printMenu(commands);

            Integer string = inputOutput.readInt("Ваш вибір: ");

            if (string == 0) {
                return;
            } else {
                Command command = commands.get(string);
                if (command != null) {
                    command.command();
                } else {
                    System.out.println("Оберіть номер з пункту!");
                }
            }
        }
    }
}
