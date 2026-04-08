package ui.command;

import domain.passenger.Passenger;
import domain.passenger.PassengerService;
import domain.ticket.*;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import infrastructure.util.TicketPrinter;

import java.util.List;

@MenuItem(action = 4, description = "Повернути квиток")
public class RemoveTicketCommand implements Command {

    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final TicketViewService ticketViewService;

    public RemoveTicketCommand(
            InputOutput inputOutput,
            TicketService ticketService,
            PassengerService passengerService,
            TicketViewService ticketViewService
    ) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.passengerService = passengerService;
        this.ticketViewService = ticketViewService;
    }

    @Override
    public void command() {

        List<TicketDTO> dtoList = ticketViewService.getAllTicketsForView();

        if (dtoList.isEmpty()) {
            System.out.println("Не має проданих квитків!");
            return;
        }

        TicketPrinter.printTicket(dtoList);

        Long ticketId = inputOutput.readLong("Введіть ID тікета: ");
        Ticket ticket = ticketService.findById(ticketId);

        if (ticket == null) {
            System.out.println("Не має такого квитка!");
            return;
        }

        String string = inputOutput.readString("Введіть ім'я та прізвище пасажира: ");
        String[] split = string.split(" ");

        if (split.length < 2) {
            System.out.println("❌ Введіть повне ім'я та прізвище через пробіл!");
            return;
        }

        Passenger passenger = passengerService.findByFistAndLastName(split[0], split[1]);

        if (passenger == null) {
            System.out.println("❌ Такого пасажира не знайдено в базі!");
            return;
        }

        try {
            ticketService.cancelTicket(ticketId, passenger.getId());
            System.out.println("✅ Квиток #" + ticketId + " успішно скасовано!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Помилка: " + e.getMessage());
        }
    }
}
