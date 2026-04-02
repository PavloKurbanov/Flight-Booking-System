package ui.command.show;

import domain.flight.Flight;
import domain.flight.FlightService;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import ui.command.Command;
import infrastructure.util.FlightPrinter;

import java.util.List;

@MenuItem(action = 1, description = "Показати всі рейси", menuGroup = MenuGroup.SHOW)
public class ShowAllFlight implements Command {
    private final FlightService flightService;

    public ShowAllFlight(FlightService flightService) {
        this.flightService = flightService;
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
