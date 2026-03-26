package domain.ticket;

import domain.flight.Flight;
import domain.flight.FlightService;
import domain.passenger.Passenger;
import domain.passenger.PassengerService;

import java.util.List;

public class TicketMapper {
    private final FlightService flightService;
    private final PassengerService passengerService;

    public TicketMapper(FlightService flightService, PassengerService passengerService) {
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    public List<TicketDTO> toDTOList(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket ->
                {
                    Flight flight = flightService.findById(ticket.getFlightId());
                    Passenger passenger = passengerService.findById(ticket.getPassengerId());

                    String safeFullName = (passenger != null)
                            ? passenger.getFirstName() + " " + passenger.getLastName() : "Не має такого користувача";

                    String safeDeparture = (flight != null) ? flight.getDepartureCity() : "N/A";
                    String safeArrival = (flight != null) ? flight.getArrivalCity() : "N/A";

                    return new TicketDTO(
                            ticket.getId(),
                            safeDeparture,
                            safeArrival,
                            safeFullName,
                            ticket.getPrice()
                    );

                }).toList();
    }
}
