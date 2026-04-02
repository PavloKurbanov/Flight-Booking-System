package ui.command;

import domain.passenger.Passenger;
import domain.passenger.PassengerService;
import domain.ticket.Ticket;
import domain.ticket.TicketDTO;
import domain.ticket.TicketMapper;
import domain.ticket.TicketService;
import framework.menuEngine.menuValidation.MenuItem;
import infrastructure.io.InputOutput;
import infrastructure.util.TicketPrinter;

import java.util.List;

@MenuItem(action = 4, description = "Повернути квиток")
public class RemoveTicketCommand implements Command {
    private final InputOutput inputOutput;
    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final TicketMapper ticketMapper;

    public RemoveTicketCommand(InputOutput inputOutput, TicketService ticketService, PassengerService passengerService, TicketMapper ticketMapper) {
        this.inputOutput = inputOutput;
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
        this.passengerService = passengerService;
    }

    @Override
    public void command() {
        List<Ticket> ticketServiceAll = ticketService.getAll();

        if (ticketServiceAll.isEmpty()) {
            System.out.println("Не має проданих квитків!");
            return;
        }
        List<TicketDTO> dtoList = ticketMapper.toDTOList(ticketServiceAll);
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

        List<Passenger> byFistAndLastName = passengerService.findByFistAndLastName(split[0], split[1]);

        if (byFistAndLastName.isEmpty()) {
            System.out.println("❌ Такого пасажира не знайдено в базі!");
            return;
        }

        boolean isOwner = byFistAndLastName.stream()
                .anyMatch(passenger -> passenger.getId().equals(ticket.getPassengerId()));

        if (!isOwner) {
            System.out.println("❌ Помилка: Цей квиток не належить пасажиру " + string + "!");
        }

        try {
            ticketService.cancelTicket(ticketId);
            System.out.println("✅ Квиток #" + ticketId + " успішно скасовано, місце повернено в літак!");
        } catch (IllegalArgumentException e) {
            System.out.println("Не вірно вказаний пасажир!");
            System.out.println("❌ Помилка:" + e.getMessage());
        }
    }
}
