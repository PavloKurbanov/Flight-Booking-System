package ui.command.show;

import domain.passenger.Passenger;
import domain.passenger.PassengerService;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import ui.command.Command;
import infrastructure.util.PassengerPrinter;

import java.util.List;

@MenuItem(action = 2, description = "Показати всіх пасажирів", menuGroup = MenuGroup.SHOW)
public class ShowAllPassenger implements Command {
    private final PassengerService passengerService;

    public ShowAllPassenger(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Override
    public void command() {
        List<Passenger> passengers = passengerService.getAll();

        if (passengers.isEmpty()) {
            System.out.println("Не має жодного пасажира.");
            return;
        }
        PassengerPrinter.printPassenger(passengers);
    }
}
