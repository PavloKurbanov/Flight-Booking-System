package ui.menu;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.MainMenuBuilder;
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
            System.out.println("1) Показати всі рейси");
            System.out.println("2) Показати всіх пасажирів");
            System.out.println("3) Показати всі квитки");
            System.out.println("4) Купити квиток");
            System.out.println("5) Повернути квиток");
            System.out.println("6) Показати квитки пасажира");
            System.out.println("7) Показати квитки на рейс");
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
