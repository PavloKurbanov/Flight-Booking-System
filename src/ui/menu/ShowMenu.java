package ui.menu;

import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;

import java.util.Map;

public class ShowMenu {
    private final InputOutput inputOutput;
    private final Map<String, Command> commands;

    public ShowMenu(InputOutput inputOutput, FlightService flightService, PassengerService passengerService, TicketService ticketService) {
        this.inputOutput = inputOutput;
        ShowMenuBuilder showMenuBuilder = new ShowMenuBuilder(inputOutput, flightService, passengerService, ticketService);
        this.commands = showMenuBuilder.showMenu();
    }

    public void showMenu() {
        while (true) {
            System.out.println("1) Показати всі рейси");
            System.out.println("2) Показати всіх пасажирів");
            System.out.println("3) Показати всі квитки");
            System.out.println("4) Показати квитки пасажира");
            System.out.println("5) Показати квитки на рейс");
            System.out.println("0) Повернутись до меню");

            String input = inputOutput.readString("Ваш вибір: ");

            if (input.equals("0")) {
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
