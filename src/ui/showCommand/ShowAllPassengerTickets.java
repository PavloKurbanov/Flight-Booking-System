package ui.showCommand;

import entity.Ticket;
import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import util.TicketPrinter;

import java.util.List;

public class ShowAllPassengerTickets implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    public ShowAllPassengerTickets(InputOutput inputOutput, TicketService ticketService, FlightService flightService, PassengerService passengerService) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    @Override
    public String choice() {
        return "6";
    }

    @Override
    public void command() {
        String s = inputOutput.readString("Введіть ім'я та прізвище пасажира: ");
        String[] split = s.split(" ");

        if(split.length < 2){
            System.out.println("❌ Помилка: Введіть і ім'я, і прізвище!");
            return;
        }

        List<Ticket> ticketsByPassenger = ticketService.getTicketsByPassenger(split[0], split[1]);

        if (ticketsByPassenger.isEmpty()) {
            System.out.println("Не має квитків на пасажира '" + split[0] + " " + split[1] + "'");
            return;
        }
        System.out.println("Інформація про квитки пасажира '" + split[0] + " " + split[1] + "'");
        TicketPrinter.printTicket(ticketsByPassenger, passengerService, flightService);
    }
}
