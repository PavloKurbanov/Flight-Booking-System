package ui.command.show;

import domain.ticket.Ticket;
import domain.ticket.TicketDTO;
import domain.ticket.TicketMapper;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import ui.command.Command;
import infrastructure.util.TicketPrinter;

import java.util.List;

public class ShowAllPassengerTickets implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public ShowAllPassengerTickets(InputOutput inputOutput, TicketService ticketService, TicketMapper ticketMapper) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.ticketMapper =  ticketMapper;
    }

    @Override
    public String choice() {
        return "4";
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

        List<TicketDTO> dtoList = ticketMapper.toDTOList(ticketsByPassenger);

        TicketPrinter.printTicket(dtoList);
    }
}
