package ui.command.show;

import domain.flight.Flight;
import domain.ticket.Ticket;
import domain.ticket.TicketDTO;
import domain.ticket.TicketMapper;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.ticket.TicketService;
import ui.command.Command;
import infrastructure.util.FlightPrinter;
import infrastructure.util.TicketPrinter;

import java.util.List;

@MenuItem(action = 5, description = "Показати квитки на рейс", menuGroup = MenuGroup.SHOW)
public class ShowAllFlightTickets implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final FlightService flightService;
    private final TicketMapper ticketMapper;

    public ShowAllFlightTickets(InputOutput inputOutput, TicketService ticketService, FlightService flightService, TicketMapper ticketMapper) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.ticketMapper = ticketMapper;
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
            System.out.println("Не має такого пасажира.");
            return;
        }

        List<Ticket> ticketsByFlight = ticketService.getTicketsByFlight(flightId);

        if (ticketsByFlight.isEmpty()) {
            System.out.println("Не має жодного квитка на цей рейс.");
            return;
        }

        List<TicketDTO> dtoList = ticketMapper.toDTOList(ticketsByFlight);

        TicketPrinter.printTicket(dtoList);
    }
}
