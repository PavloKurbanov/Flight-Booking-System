package ui.command.show;

import domain.ticket.*;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import ui.command.Command;
import infrastructure.util.TicketPrinter;

import java.util.List;

@MenuItem(action = 3, description = "Показати всі квитки", menuGroup = MenuGroup.SHOW)
public class ShowAllTicket implements Command {
    private final TicketViewService ticketViewService;

    public ShowAllTicket(TicketViewService ticketViewService) {
        this.ticketViewService = ticketViewService;
    }

    @Override
    public void command() {
        List<TicketDTO> allTicketsForView = ticketViewService.getAllTicketsForView();

        if (allTicketsForView.isEmpty()) {
            System.out.println("Немає проданих квитків.");
            return;
        }
        TicketPrinter.printTicket(allTicketsForView);
    }
}
