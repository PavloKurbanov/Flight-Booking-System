package ui;

import entity.Passenger;
import service.PassengerService;
import ui.command.Command;
import util.PassengerPrinter;

import java.util.List;

public class ShowAllPassenger implements Command {
    private final PassengerService passengerService;

    public ShowAllPassenger(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Override
    public String choice() {
        return "";
    }

    @Override
    public void command() {
        List<Passenger> passengerServiceAll = passengerService.getAll();

        if(passengerServiceAll.isEmpty()){
            System.out.println("Не має жодного пасажира");
            return;
        }
        PassengerPrinter.printPassenger(passengerServiceAll);
    }
}
