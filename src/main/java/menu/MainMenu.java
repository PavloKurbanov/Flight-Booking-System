package menu;

import domain.ticket.TicketMapper;
import framework.menuPrinter.MenuPrinter;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.Command;

import java.util.Map;

public class MainMenu {
    private final InputOutput inputOutput;
    private final Map<Integer, Command> commandMap;

    public MainMenu(InputOutput inputOutput, FlightService flightService, PassengerService passengerService, TicketService ticketService, TicketMapper ticketMapper) {
        this.inputOutput = inputOutput;
        MainMenuBuilder mainMenuBuilder = new MainMenuBuilder(inputOutput, flightService, passengerService, ticketService, ticketMapper);
        this.commandMap = mainMenuBuilder.buildCommands();
    }

    public void showMenu() {
        while (true) {
            MenuPrinter.printMenu(commandMap);

            Integer choice = inputOutput.readInt("Ваш вибір: ");

            if (choice == 0) {
                System.out.println("На все добре");
                return;
            } else {
                Command command = commandMap.get(choice);
                if (command != null) {
                    command.command();
                } else {
                    System.out.println("Введіть номер з пункту!");
                }
            }
        }
    }
}
