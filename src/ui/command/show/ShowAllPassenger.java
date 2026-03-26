package ui.command.show;

import domain.passenger.Passenger;
import domain.passenger.PassengerService;
import ui.command.Command;
import infrastructure.util.PassengerPrinter;

import java.util.List;

public class ShowAllPassenger implements Command {
    private final PassengerService passengerService;

    public ShowAllPassenger(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Override
    public String choice() {
        return "2";
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
