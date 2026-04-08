package domain.ticket;

import domain.flight.Flight;
import domain.flight.FlightService;
import domain.passenger.Passenger;
import domain.passenger.PassengerService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketViewService {

    private final TicketService ticketService;
    private final FlightService flightService;
    private final PassengerService passengerService;

    public TicketViewService(
            TicketService ticketService,
            FlightService flightService,
            PassengerService passengerService
    ) {
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.passengerService = passengerService;
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
        // 1. Витягуємо всі унікальні ID для Batch Fetching
        List<Long> flightIds = tickets.stream()
                .map(Ticket::getFlightId)
                .distinct()
                .toList();

        List<Long> passengerIds = tickets.stream()
                .map(Ticket::getPassengerId)
                .distinct()
                .toList();

        // 2. Отримуємо всі Flights та Passengers одним масовим запитом
        Map<Long, Flight> flightMap = flightService.findAllByIds(flightIds)
                .stream()
                .collect(Collectors.toMap(Flight::getId, f -> f));

        Map<Long, Passenger> passengerMap = passengerService.findAllByIds(passengerIds)
                .stream()
                .collect(Collectors.toMap(Passenger::getId, p -> p));

        // 3. Формуємо DTO для UI
        return tickets.stream()
                .map(ticket -> {
                    Flight flight = flightMap.get(ticket.getFlightId());
                    Passenger passenger = passengerMap.get(ticket.getPassengerId());

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