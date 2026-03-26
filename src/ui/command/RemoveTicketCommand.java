package ui.command;

import domain.flight.Flight;
import domain.passenger.Passenger;
import domain.ticket.Ticket;
import domain.ticket.TicketDTO;
import infrastructure.io.InputOutput;
import domain.flight.FlightService;
import domain.passenger.PassengerService;
import domain.ticket.TicketService;
import infrastructure.util.TicketPrinter;

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

        List<TicketDTO> dtoList = ticketServiceAll.stream()
                .map(ticket ->
                {
                    Passenger passenger = passengerService.findById(ticket.getPassengerId());
                    Flight flight = flightService.findById(ticket.getFlightId());

                    String getFullName = passenger.getFirstName() + " " + passenger.getLastName();

                    return new TicketDTO(
                            ticket.getId(),
                            flight.getDepartureCity(),
                            flight.getArrivalCity(),
                            getFullName,
                            ticket.getPrice()
                    );
                }).toList();

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
