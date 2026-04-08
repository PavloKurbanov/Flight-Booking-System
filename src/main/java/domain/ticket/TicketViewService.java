package domain.ticket;

import domain.flight.Flight;
import domain.passenger.Passenger;

import java.util.List;

public class TicketViewService {

    private final TicketService ticketService;

    public TicketViewService(
            TicketService ticketService
    ) {
        this.ticketService = ticketService;
    }

    /**
     * Отримує всі квитки для UI.
     */
    public List<TicketDTO> getAllTicketsForView() {
        List<Ticket> tickets = ticketService.getAll();

        if (tickets.isEmpty()) {
            return List.of();
        }

        return convertToDTOs(tickets);
    }

    /**
     * Отримує квитки конкретного пасажира для UI.
     */
    public List<TicketDTO> getPassengerTicketsForView(String firstName, String lastName) {
        List<Ticket> tickets = ticketService.getTicketsByPassenger(firstName, lastName);

        if (tickets.isEmpty()) {
            return List.of();
        }

        return convertToDTOs(tickets);
    }

    /**
     * Отримує квитки конкретного рейсу для UI.
     */
    public List<TicketDTO> getFlightTicketsForView(Long flightId) {
        List<Ticket> tickets = ticketService.getTicketsByFlight(flightId);

        if (tickets.isEmpty()) {
            return List.of();
        }

        return convertToDTOs(tickets);
    }

    /**
     * ПРИВАТНИЙ МЕТОД-ПОМІЧНИК (Helper)
     * Єдине місце в системі, яке відповідає за мапінг Ticket -> TicketDTO
     * та вирішення проблеми N+1.
     * Складність: O(N) по пам'яті та часу, але всього 3 запити до "БД" (Batch Fetching).
     */
    private List<TicketDTO> convertToDTOs(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> {
                    Flight flight = ticket.getFlight();
                    Passenger passenger = ticket.getPassenger();

                    String fullName = (passenger != null)
                            ? passenger.getFirstName() + " " + passenger.getLastName()
                            : "Невідомий пасажир";

                    String departureCity = (flight != null) ? flight.getDepartureCity() : "N/A";
                    String arrivalCity = (flight != null) ? flight.getArrivalCity() : "N/A";

                    return new TicketDTO(
                            ticket.getId(),
                            departureCity,
                            arrivalCity,
                            fullName
                    );
                })
                .toList();
    }
}