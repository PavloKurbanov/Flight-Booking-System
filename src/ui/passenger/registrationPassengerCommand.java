package ui.passenger;

import entity.Passenger;
import io.InputOutput;
import service.PassengerService;
import ui.command.Command;

public class registrationPassengerCommand implements Command {
    private final InputOutput inputOutput;
    private final PassengerService passengerService;

    public registrationPassengerCommand(InputOutput inputOutput, PassengerService passengerService) {
        this.inputOutput = inputOutput;
        this.passengerService = passengerService;
    }

    @Override
    public String choice() {
        return "";
    }

    @Override
    public void command() {
        String firstName = inputOutput.readString("Введіть ім'я пасажира: ");
        String lastName = inputOutput.readString("Введіть прізвище пасажира: ");

        try {
            passengerService.save(new Passenger(null, firstName, lastName));
            System.out.println("Пасажира " + firstName + " " + lastName);
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА: " + e.getMessage());
        }
    }
}
