package ui.command;

import domain.passenger.Passenger;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import domain.passenger.PassengerService;

@MenuItem(action = 1,description = "Зареєструвати пасажира", menuGroup = MenuGroup.REGISTRATION)
public class RegistrationPassengerCommand implements Command {
    private final InputOutput inputOutput;
    private final PassengerService passengerService;

    public RegistrationPassengerCommand(InputOutput inputOutput, PassengerService passengerService) {
        this.inputOutput = inputOutput;
        this.passengerService = passengerService;
    }

    @Override
    public void command() {
        String firstName = inputOutput.readString("Введіть ім'я пасажира: ");
        String lastName = inputOutput.readString("Введіть прізвище пасажира: ");

        try {
            passengerService.save(new Passenger(null, firstName, lastName));
            System.out.println("Пасажира " + firstName + " " + lastName + " успішно зареєстровано!");
        } catch (IllegalArgumentException e) {
            System.out.println("ПОМИЛКА: " + e.getMessage());
        }
    }
}
