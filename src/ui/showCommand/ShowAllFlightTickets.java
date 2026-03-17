package ui.showCommand;

import entity.Flight;
import entity.Ticket;
import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import util.FlightPrinter;
import util.TicketPrinter;

import java.util.List;

public class ShowAllFlightTickets implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    public ShowAllFlightTickets(InputOutput inputOutput, TicketService ticketService, FlightService flightService, PassengerService passengerService) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    @Override
    public String choice() {
        return "7";
    }

    @Override
    public void command() {
        List<Flight> flightList = flightService.getAll();

        if(flightList.isEmpty()){
            System.out.println("Не зареєстровано жодного рейсу.");
            return;
        }
        FlightPrinter.printFlights(flightList);

        Long flightId = inputOutput.readLong("Введіть ID рейсу: ");
        if(flightId == null){
            System.out.println("Не має такого пасажира.");
            return;
        }

        List<Ticket> ticketsByFlight = ticketService.getTicketsByFlight(flightId);
        if(ticketsByFlight.isEmpty()){
            System.out.println("Не має жодного квитка на цей рейс.");
            return;
        }
        TicketPrinter.printTicket(ticketsByFlight, passengerService,  flightService);
    }
}
