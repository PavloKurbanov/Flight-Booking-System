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
        this.inputOutput = inputOutput;
        RegistrationMenuBuilder registrationMenuBuilder = new RegistrationMenuBuilder(inputOutput, flightService, ticketService, passengerService);
        this.commands = registrationMenuBuilder.showMenu();
    }

    public void showMenu() {
        while (true) {
            System.out.println("1) Зареєструвати пасажира");
            System.out.println("2) Зареєструвати рейс");
            System.out.println("0) Повернутись до меню");

            String string = inputOutput.readString("Ваш вибір: ");

            if (string.equals("0")) {
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
