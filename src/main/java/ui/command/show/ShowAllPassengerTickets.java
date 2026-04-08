package ui.command.show;

import domain.ticket.*;
import framework.menuEngine.menuValidation.MenuGroup;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import ui.command.Command;
import infrastructure.util.TicketPrinter;

import java.util.List;

@MenuItem(action = 4, description = "Показати квитки пасажира", menuGroup = MenuGroup.SHOW)
public class ShowAllPassengerTickets implements Command {
    private final InputOutput inputOutput;
    private final TicketViewService  ticketViewService;

    public ShowAllPassengerTickets(InputOutput inputOutput, TicketViewService  ticketViewService) {
        this.inputOutput = inputOutput;
        this.ticketViewService =  ticketViewService;
    }
    @Override
    public void command() {
        String s = inputOutput.readString("Введіть ім'я та прізвище пасажира: ");
        String[] split = s.split(" ");

        if(split.length < 2){
            System.out.println("❌ Помилка: Введіть і ім'я, і прізвище!");
            return;
        }

        List<TicketDTO> tickets = ticketViewService.getPassengerTicketsForView(split[0], split[1]);

        if (tickets.isEmpty()) {
            System.out.println("Немає квитків на пасажира '" + split[0] + " " + split[1] + "'");
            return;
        }

        System.out.println("Інформація про квитки пасажира '" + split[0] + " " + split[1] + "':");
        TicketPrinter.printTicket(tickets);
    }
}
