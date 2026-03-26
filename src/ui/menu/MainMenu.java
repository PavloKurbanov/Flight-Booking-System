package ui.menu;

import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.Command;

import java.util.Map;

public class MainMenu {
    private final InputOutput inputOutput;
    private final Map<String, Command> commandMap;

    public MainMenu(InputOutput inputOutput, FlightService flightService, PassengerService passengerService, TicketService ticketService) {
        this.inputOutput = inputOutput;
        MainMenuBuilder mainMenuBuilder = new MainMenuBuilder(inputOutput, flightService, passengerService, ticketService);
        this.commandMap = mainMenuBuilder.buildCommands();
    }

    public void showMenu() {
        while (true) {
            System.out.println("1) Реєстрація");
            System.out.println("2) Показати інформацію");
            System.out.println("3) Купити квиток");
            System.out.println("4) Повернути квиток");
            System.out.println("0) Вийти");

            String choice = inputOutput.readString("Ваш вибір: ");

            if (choice.equals("0")) {
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
