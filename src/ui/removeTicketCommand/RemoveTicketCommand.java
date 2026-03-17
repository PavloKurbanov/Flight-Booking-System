package ui.removeTicketCommand;

import com.sun.source.tree.TryTree;
import entity.Ticket;
import io.InputOutput;
import service.FlightService;
import service.PassengerService;
import service.TicketService;
import ui.command.Command;
import util.TicketPrinter;

import java.util.List;

public class RemoveTicketCommand implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final FlightService flightService;

    public RemoveTicketCommand(InputOutput inputOutput, TicketService ticketService, PassengerService passengerService, FlightService flightService) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

    @Override
    public String choice() {
        return "5";
    }

    @Override
    public void command() {
        List<Ticket> ticketServiceAll = ticketService.getAll();

        if (ticketServiceAll.isEmpty()) {
            System.out.println("Немає проданих квитків.");
            return;
        }
        TicketPrinter.printTicket(ticketServiceAll, passengerService, flightService);
        Long ticketId = inputOutput.readLong("Введіть ID тікета: ");

        try {
            ticketService.cancelTicket(ticketId);
            System.out.println("✅ Квиток #" + ticketId + " успішно скасовано, місце повернено в літак!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка:" + e.getMessage());
        }
    }
}
