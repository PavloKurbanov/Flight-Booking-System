package ui.command.show;

import domain.ticket.Ticket;
import domain.ticket.TicketDTO;
import domain.ticket.TicketMapper;
import domain.ticket.TicketService;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import ui.command.Command;
import infrastructure.util.TicketPrinter;

import java.util.List;

@MenuItem(action = 3, description = "Показати всі квитки", menuGroup = MenuGroup.SHOW)
public class ShowAllTicket implements Command {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public ShowAllTicket(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public void command() {
        List<Ticket> tickets = ticketService.getAll();

        if (tickets.isEmpty()) {
            System.out.println("Немає проданих квитків.");
            return;
        }
        List<TicketDTO> dtoList = ticketMapper.toDTOList(tickets);
        TicketPrinter.printTicket(dtoList);
    }
}
