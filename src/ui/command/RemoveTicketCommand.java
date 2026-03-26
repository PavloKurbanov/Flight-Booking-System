package ui.command;

import domain.ticket.Ticket;
import domain.ticket.TicketDTO;
import domain.ticket.TicketMapper;
import domain.ticket.TicketService;
import infrastructure.io.InputOutput;
import infrastructure.util.TicketPrinter;
import java.util.List;

public class RemoveTicketCommand implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public RemoveTicketCommand(InputOutput inputOutput, TicketService ticketService, TicketMapper ticketMapper) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public String choice() {
        return "5";
    }

    @Override
    public void command() {
        List<Ticket> ticketServiceAll = ticketService.getAll();

        List<TicketDTO> dtoList = ticketMapper.toDTOList(ticketServiceAll);

        TicketPrinter.printTicket(dtoList);

        Long ticketId = inputOutput.readLong("Введіть ID тікета: ");

        try {
            ticketService.cancelTicket(ticketId);
            System.out.println("✅ Квиток #" + ticketId + " успішно скасовано, місце повернено в літак!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка:" + e.getMessage());
        }
    }
}
