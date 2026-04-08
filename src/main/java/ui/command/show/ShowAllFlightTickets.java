package ui.command.show;

import domain.flight.Flight;
import domain.ticket.*;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import ui.command.Command;
import infrastructure.util.FlightPrinter;
import infrastructure.util.TicketPrinter;

import java.util.List;

@MenuItem(action = 5, description = "Показати квитки на рейс", menuGroup = MenuGroup.SHOW)
public class ShowAllFlightTickets implements Command {
    private final InputOutput inputOutput;

    private final FlightService flightService;
    private final TicketViewService ticketViewService;

    public ShowAllFlightTickets(InputOutput inputOutput, FlightService flightService, TicketViewService ticketViewService) {
        this.inputOutput = inputOutput;
        this.flightService = flightService;
        this.ticketViewService = ticketViewService;
    }

    @Override
    public void command() {
        List<Flight> flightList = flightService.getAll();

        if (flightList.isEmpty()) {
            System.out.println("Не зареєстровано жодного рейсу.");
            return;
        }
        FlightPrinter.printFlights(flightList);

        Long flightId = inputOutput.readLong("Введіть ID рейсу: ");
        if (flightId == null) {
            System.out.println("Не має такого рейсу.");
            return;
        }

        List<TicketDTO> flightTicketsForView = ticketViewService.getFlightTicketsForView(flightId);

        if (flightTicketsForView.isEmpty()) {
            System.out.println("Не має жодного квитка на цей рейс.");
            return;
        }

        TicketPrinter.printTicket(flightTicketsForView);
    }
}
