package ui.showCommand;

import io.InputOutput;
import service.TicketService;
import ui.command.Command;

public class ShowAllFlightTickets implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;

    public ShowAllFlightTickets(InputOutput inputOutput, TicketService ticketService) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
    }

    @Override
    public String choice() {
        return "7";
    }

    @Override
    public void command() {
        inputOutput.readLong("Введіть")
    }
}
