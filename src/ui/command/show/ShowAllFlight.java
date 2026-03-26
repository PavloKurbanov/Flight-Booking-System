package ui.command.show;

import domain.flight.Flight;
import domain.flight.FlightService;
import ui.command.Command;
import infrastructure.util.FlightPrinter;

import java.util.List;

public class ShowAllFlight implements Command {
    private final FlightService flightService;

    public ShowAllFlight(FlightService flightService) {
        this.flightService = flightService;
    }

    @Override
    public String choice() {
        return "1";
    }

    @Override
    public void command() {
        List<Flight> flightList = flightService.getAll();

        if (flightList.isEmpty()) {
            System.out.println("На даний момент не має жодного рейсу");
            return;
        }
        FlightPrinter.printFlights(flightList);
    }
}
