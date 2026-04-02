package domain.ticket;

public record TicketDTO(Long id, String departureCity, String arrivalCity, String passengerFullName) {
}
