package ui.menu;

import domain.ticket.TicketMapper;
import framework.menuPrinter.MenuPrinter;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.Command;

import java.util.Map;

public class ShowMenu {
    private final InputOutput inputOutput;
    private final Map<Integer, Command> commands;

    public ShowMenu(InputOutput inputOutput, FlightService flightService, PassengerService passengerService, TicketService ticketService, TicketMapper ticketMapper) {
        this.inputOutput = inputOutput;
        ShowMenuBuilder showMenuBuilder = new ShowMenuBuilder(inputOutput, flightService, passengerService, ticketService, ticketMapper);
        this.commands = showMenuBuilder.showMenu();
    }

    public void showMenu() {
        while (true) {
            MenuPrinter.printMenu(commands);

            Integer input = inputOutput.readInt("Ваш вибір: ");

            if (input == 0) {
                return;
            } else {
                Command command = commands.get(input);
                if (command != null) {
                    command.command();
                } else {
                    System.out.println("Введіть номер з пункту!");
                }
            }
        }
    }
}
